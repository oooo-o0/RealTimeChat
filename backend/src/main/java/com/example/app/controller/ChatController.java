package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.exception.ChatException;
import com.example.app.exception.UserException;
import com.example.app.model.Chat;
import com.example.app.model.User;
import com.example.app.payload.ApiResponse;
import com.example.app.payload.GroupChatRequest;
import com.example.app.payload.SingleChatRequest;
import com.example.app.serviceimpl.ChatServiceImpl;
import com.example.app.serviceimpl.UserServiceImpl;

@RestController // RESTful APIのコントローラーとして動作するクラス
@RequestMapping("/api/chats") // このコントローラーのエンドポイントの基本パスを設定
public class ChatController {

    @Autowired
    private ChatServiceImpl chatService; // チャット関連のサービス

    @Autowired
    private UserServiceImpl userService; // ユーザー関連のサービス

    /**
     * シングルチャット（1対1のチャット）を作成する
     * @param singleChatRequest チャット相手のユーザーIDを含むリクエスト
     * @param jwt 認証トークン（ユーザー認証用）
     * @return 作成されたチャットの情報を返す
     */
    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
            @RequestHeader("Authorization") String jwt) throws UserException {

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // 指定されたユーザーとのチャットを作成
        Chat chat = this.chatService.createChat(reqUser, singleChatRequest.getUserId());

        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    /**
     * グループチャットを作成する
     * @param groupChatRequest グループ情報を含むリクエスト
     * @param jwt 認証トークン（ユーザー認証用）
     * @return 作成されたグループチャットの情報を返す
     */
    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
            @RequestHeader("Authorization") String jwt) throws UserException {

        System.out.println(groupChatRequest);

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // グループチャットを作成
        Chat chat = this.chatService.createGroup(groupChatRequest, reqUser);

        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    /**
     * 指定したIDのチャットを取得する
     * @param chatId 取得したいチャットのID
     * @return チャット情報を返す
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable int chatId) throws ChatException {

        Chat chat = this.chatService.findChatById(chatId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    /**
     * ログインユーザーが参加しているチャットのリストを取得する
     * @param jwt 認証トークン（ユーザー認証用）
     * @return ユーザーが参加しているチャット一覧
     */
    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
            throws UserException {

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // ユーザーが参加しているチャット一覧を取得
        List<Chat> chats = this.chatService.findAllChatByUserId(reqUser.getId());

        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    /**
     * グループチャットにユーザーを追加する
     * @param chatId 追加するグループのID
     * @param userId 追加するユーザーのID
     * @param jwt 認証トークン（操作するユーザーの認証）
     * @return 更新されたグループチャットの情報
     */
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,
            @PathVariable Integer userId, @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // 指定されたユーザーをグループチャットに追加
        Chat chat = this.chatService.addUserToGroup(userId, chatId, reqUser);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    /**
     * グループチャットからユーザーを削除する
     * @param chatId 対象のグループチャットID
     * @param userId 削除するユーザーID
     * @param jwt 認証トークン（操作するユーザーの認証）
     * @return 更新されたグループチャットの情報
     */
    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,
            @PathVariable Integer userId, @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // 指定されたユーザーをグループチャットから削除
        Chat chat = this.chatService.removeFromGroup(userId, chatId, reqUser);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    /**
     * チャットを削除する
     * @param chatId 削除するチャットのID
     * @param jwt 認証トークン（操作するユーザーの認証）
     * @return 削除結果のメッセージ
     */
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        // JWTからユーザー情報を取得
        User reqUser = this.userService.findUserProfile(jwt);

        // 指定されたチャットを削除
        this.chatService.deleteChat(chatId, reqUser.getId());

        // 削除成功のレスポンスを返す
        ApiResponse res = new ApiResponse("Deleted Successfully...", false);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
