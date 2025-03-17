package com.example.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.app.exception.ChatException;
import com.example.app.exception.MessageException;
import com.example.app.exception.UserException;
import com.example.app.model.Chat;
import com.example.app.model.Message;
import com.example.app.model.User;
import com.example.app.payload.SendMessageRequest;
import com.example.app.repository.MessageRepository;
import com.example.app.service.MessageService;
/**
 * MessageServiceImpl クラスは、メッセージ関連の処理を提供するサービスクラス。
 * メッセージの送信、取得、検索、削除などの機能を実装。
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository; // メッセージのデータ操作を行うリポジトリ

    @Autowired
    private UserServiceImpl userService; // ユーザー情報を取得するサービス

    @Autowired
    private ChatServiceImpl chatService; // チャット情報を取得するサービス

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // WebSocket 通信のためのメッセージングテンプレート

    /**
     * メッセージを送信する。
     * 
     * @param req メッセージ送信リクエスト (ユーザーID、チャットID、メッセージ内容)
     * @return 送信されたメッセージオブジェクト
     * @throws UserException ユーザーが見つからない場合に発生
     * @throws ChatException チャットが見つからない場合に発生
     */
    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        // ユーザーとチャットを取得
        User user = this.userService.findUserById(req.getUserId());
        Chat chat = this.chatService.findChatById(req.getChatId());

        // 新しいメッセージオブジェクトを作成
        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());

        // データベースにメッセージを保存
        message = this.messageRepository.save(message);

        // WebSocket を使ってメッセージをリアルタイム配信
        if (chat.isGroup()) {
            messagingTemplate.convertAndSend("/group/" + chat.getId(), message);
        } else {
            messagingTemplate.convertAndSend("/user/" + chat.getId(), message);
        }

        return message;
    }

    /**
     * 指定したチャットのメッセージ一覧を取得する。
     * 
     * @param chatId チャットID
     * @param reqUser リクエストユーザー
     * @return 指定されたチャットのメッセージリスト
     * @throws ChatException チャットが見つからない場合に発生
     * @throws UserException ユーザーがチャットに関連していない場合に発生
     */
    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        // チャットを取得
        Chat chat = this.chatService.findChatById(chatId);

        // ユーザーがチャットに関連しているかチェック
        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You are not related to this chat");
        }

        // チャットに関連するメッセージを取得
        List<Message> messages = this.messageRepository.findByChatId(chat.getId());

        return messages;
    }

    /**
     * 指定したメッセージIDのメッセージを取得する。
     * 
     * @param messageId メッセージID
     * @return 取得したメッセージオブジェクト
     * @throws MessageException メッセージが見つからない場合に発生
     */
    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        return this.messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("The required message is not found"));
    }

    /**
     * 指定したメッセージを削除する。
     * 
     * @param messageId 削除するメッセージのID
     * @param reqUser リクエストユーザー
     * @throws MessageException ユーザーがメッセージの所有者でない場合やメッセージが存在しない場合に発生
     */
    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException {
        // メッセージを取得
        Message message = this.messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("The required message is not found"));

        // メッセージの所有者かどうかを確認し、削除を実行
        if (message.getUser().getId() == reqUser.getId()) {
            this.messageRepository.delete(message);
        } else {
            throw new MessageException("You are not authorized for this task");
        }
    }
}
