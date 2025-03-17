package com.example.app.service;

import java.util.List;

import com.example.app.exception.ChatException;
import com.example.app.exception.MessageException;
import com.example.app.exception.UserException;
import com.example.app.model.Message;
import com.example.app.model.User;
import com.example.app.payload.SendMessageRequest;

/**
 * MessageService インターフェースは、メッセージの送信・取得・削除に関するサービスを提供する。
 * 具体的な処理は実装クラスで定義される。
 */
public interface MessageService {
	 /**
     * メッセージを送信する。
     * 
     * @param req メッセージ送信リクエスト（送信者、宛先、内容などを含む）
     * @return 送信されたメッセージオブジェクト
     * @throws UserException 送信者が存在しない場合に発生
     * @throws ChatException 宛先チャットが存在しない場合に発生
     */
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

    /**
     * 指定したチャットに関連するすべてのメッセージを取得する。
     * 
     * @param chatId メッセージを取得したいチャットの ID
     * @param reqUser リクエストしたユーザー（アクセス権の確認に使用）
     * @return チャット内のメッセージリスト
     * @throws ChatException 指定したチャットが見つからない場合に発生
     * @throws UserException ユーザーがチャットに参加していない場合に発生
     */
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    /**
     * 指定した ID のメッセージを取得する。
     * 
     * @param messageId 取得したいメッセージの ID
     * @return 該当するメッセージオブジェクト
     * @throws MessageException メッセージが見つからない場合に発生
     */
    public Message findMessageById(Integer messageId) throws MessageException;

    /**
     * 指定した ID のメッセージを削除する。
     * 
     * @param messageId 削除したいメッセージの ID
     * @param reqUser メッセージ削除をリクエストしたユーザー（削除権限を確認）
     * @throws MessageException メッセージが見つからない場合に発生
     */
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException;

}
