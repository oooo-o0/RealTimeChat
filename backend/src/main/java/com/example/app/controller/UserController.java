package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.exception.UserException;
import com.example.app.model.User;
import com.example.app.payload.ApiResponse;
import com.example.app.payload.UpdateUserRequest;
import com.example.app.serviceimpl.UserServiceImpl;


/**
 * ユーザー情報を管理する REST API コントローラー
 * - プロフィール取得
 * - ユーザー検索
 * - ユーザー情報更新
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * ユーザーのプロフィール情報を取得する
     * 
     * @param token JWT トークン（Authorization ヘッダーから取得）
     * @return ユーザー情報
     * @throws UserException ユーザーが見つからない場合の例外
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token)
            throws UserException {

        User user = this.userService.findUserProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * クエリ（キーワード）に基づいてユーザーを検索する
     * 
     * @param query 検索キーワード（名前またはメール）
     * @return 該当するユーザーのリスト
     */
    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {

        List<User> users = this.userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * ユーザー情報を更新する
     * 
     * @param request 更新するユーザー情報（名前・プロフィール）
     * @param token JWT トークン（Authorization ヘッダーから取得）
     * @return 更新成功メッセージ
     * @throws UserException ユーザーが見つからない場合の例外
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest request,
            @RequestHeader("Authorization") String token) throws UserException {

        // JWT からユーザー情報を取得
        User user = this.userService.findUserProfile(token);

        // ユーザー情報を更新
        this.userService.updateUser(user.getId(), request);

        // レスポンスの作成
        ApiResponse response = new ApiResponse();
        response.setMessage("User updated Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
    }
}

