package com.example.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.exception.ChatException;
import com.example.app.exception.UserException;
import com.example.app.model.Chat;
import com.example.app.model.User;
import com.example.app.payload.GroupChatRequest;
import com.example.app.repository.ChatRepository;
import com.example.app.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ChatRepository chatRepository;

    /**
     * 2人のユーザー間の個別チャットを作成する
     */
    @Override
    public Chat createChat(User reqUser, Integer userId) throws UserException {
        User user = this.userService.findUserById(userId);

        // 既にチャットが存在するか確認
        Chat isChatExist = this.chatRepository.findSingleChatByUserIds(user, reqUser);

        if (isChatExist != null) {
            return isChatExist; // 既存のチャットを返す
        }

        // 新しいチャット作成
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return this.chatRepository.save(chat);
    }

    /**
     * チャットIDを指定してチャットを取得する
     */
    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        return this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The requested chat is not found"));
    }

    /**
     * ユーザーが参加しているすべてのチャットを取得する
     */
    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = this.userService.findUserById(userId);
        return this.chatRepository.findChatByUserId(user.getId());
    }

    /**
     * グループチャットを作成する
     */
    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatImage(req.getChatImage());
        group.setChatName(req.getChatName());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);

        for (Integer userId : req.getUserIds()) {
            User user = this.userService.findUserById(userId);
            group.getUsers().add(user);
        }

        return this.chatRepository.save(group);
    }

    /**
     * グループにユーザーを追加する（管理者のみ）
     */
    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The expected chat is not found"));

        User user = this.userService.findUserById(userId);

        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().add(user);
            return this.chatRepository.save(chat);
        } else {
            throw new UserException("You do not have access to add a user");
        }
    }

    /**
     * グループの名前を変更する
     */
    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The expected chat is not found"));

        if (chat.getUsers().contains(reqUser)) {
            chat.setChatName(groupName);
            return this.chatRepository.save(chat);
        } else {
            throw new UserException("You are not authorized to rename this group");
        }
    }

    /**
     * グループからユーザーを削除する（管理者のみ）
     */
    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The expected chat is not found"));

        User user = this.userService.findUserById(userId);

        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().remove(user);
            return chat;
        } else if (chat.getUsers().contains(reqUser)) {
        	if (user.getId() == reqUser.getId()) {
                chat.getUsers().remove(user);
                return this.chatRepository.save(chat);
            }
        }
        throw new UserException("You do not have access to remove this user");
    }

    /**
     * チャットを削除する
     */
    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The expected chat is not found while deleting"));
        this.chatRepository.delete(chat);
    }
}
