package com.example.app.service;
import java.util.List;

import com.example.app.exception.UserException;
import com.example.app.model.User;
import com.example.app.payload.UpdateUserRequest; 
/**
 * ユーザー情報を操作するサービスインターフェース
 * - ユーザーの取得・更新・検索などの機能を提供
 */

public interface UserService {
	/**
     * IDを指定してユーザーを検索
     * @param id ユーザーID
     * @return 該当ユーザー情報
     * @throws UserException ユーザーが見つからない場合に発生
     */
    public User findUserById(Integer id) throws UserException;

    /**
     * JWT を使用して現在のログインユーザーのプロフィールを取得
     * @param jwt JWTトークン
     * @return ログイン中のユーザー情報
     * @throws UserException 認証エラーやユーザーが存在しない場合に発生
     */
    public User findUserProfile(String jwt) throws UserException;

    /**
     * ユーザー情報を更新
     * @param userId 更新対象のユーザーID
     * @param req 更新リクエスト（名前・プロフィール画像など）
     * @return 更新後のユーザー情報
     * @throws UserException ユーザーが見つからない場合に発生
     */
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    /**
     * ユーザーを名前またはメールアドレスで検索
     * @param query 検索クエリ（部分一致検索）
     * @return 該当するユーザーのリスト
     */
    public List<User> searchUser(String query);
}