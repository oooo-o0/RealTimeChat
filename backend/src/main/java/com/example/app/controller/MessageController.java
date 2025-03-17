package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.exception.ChatException;
import com.example.app.exception.MessageException;
import com.example.app.exception.UserException;
import com.example.app.model.Message;
import com.example.app.model.User;
import com.example.app.payload.ApiResponse;
import com.example.app.payload.SendMessageRequest;
import com.example.app.serviceimpl.MessageServiceImpl;
import com.example.app.serviceimpl.UserServiceImpl;

/**
 * メッセージに関するAPIを提供するコントローラー。
 * ユーザーはメッセージの送信、取得、削除が可能。
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 新しいメッセージを送信するエンドポイント。
     * @param sendMessageRequest 送信メッセージ情報
     * @param jwt ユーザー認証用JWT
     * @return 作成されたメッセージ
     * @throws UserException ユーザーが存在しない場合
     * @throws ChatException チャットが存在しない場合
     */
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest sendMessageRequest,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        // JWTからユーザー情報を取得
        User user = this.userService.findUserProfile(jwt);

        // ユーザーIDをリクエストにセット
        sendMessageRequest.setUserId(user.getId());

        // メッセージ送信処理
        Message message = this.messageService.sendMessage(sendMessageRequest);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 指定されたチャットのメッセージ一覧を取得するエンドポイント。
     * @param chatId チャットID
     * @param jwt ユーザー認証用JWT
     * @return 指定チャットのメッセージリスト
     * @throws UserException ユーザーが存在しない場合
     * @throws ChatException チャットが存在しない場合
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        // JWTからユーザー情報を取得
        User user = this.userService.findUserProfile(jwt);

        // 指定されたチャットのメッセージを取得
        List<Message> messages = this.messageService.getChatsMessages(chatId, user);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * 指定されたメッセージを削除するエンドポイント。
     * @param messageId メッセージID
     * @param jwt ユーザー認証用JWT
     * @return 削除成功メッセージ
     * @throws UserException ユーザーが存在しない場合
     * @throws ChatException チャットが存在しない場合
     * @throws MessageException メッセージが存在しない、または削除権限がない場合
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {

        // JWTからユーザー情報を取得
        User user = this.userService.findUserProfile(jwt);

        // メッセージを削除
        this.messageService.deleteMessage(messageId, user);

        // レスポンスメッセージを作成
        ApiResponse res = new ApiResponse("Deleted successfully......", false);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
