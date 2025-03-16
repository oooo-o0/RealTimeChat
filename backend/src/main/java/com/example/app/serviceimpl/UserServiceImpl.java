package com.example.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.example.app.exception.UserException;
import com.example.app.payload.UpdateUserRequest;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;

/**
 * ユーザーに関するビジネスロジックを提供するサービスクラス
 * - ユーザー情報の取得・更新・検索を担当
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // ユーザーデータを取得・保存するリポジトリ

    @Autowired
    private TokenProvider tokenProvider; // JWTトークンの解析を行うクラス

    /**
     * 指定された ID のユーザーを取得する
     * 
     * @param id ユーザーの ID
     * @return User ユーザー情報
     * @throws UserException ユーザーが見つからない場合に発生
     */
    @Override
    public User findUserById(Integer id) throws UserException {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserException("The requested user is not found"));
    }

    /**
     * JWT トークンからユーザーのプロフィールを取得する
     * 
     * @param jwt ユーザーの JWT トークン
     * @return User ユーザー情報
     * @throws UserException ユーザーが見つからない場合に発生
     */
    @Override
    public User findUserProfile(String jwt) throws UserException {
        // トークンからメールアドレスを取得
        String email = this.tokenProvider.getEmailFromToken(jwt);

        // トークンが無効な場合は例外を投げる
        if (email == null) {
            throw new BadCredentialsException("Received invalid token...");
        }

        // メールアドレスを元にユーザー情報を取得
        User user = this.userRepository.findByEmail(email);

        // ユーザーが見つからない場合は例外を投げる
        if (user == null) {
            throw new UserException("User not found with the provided email");
        }

        return user;
    }

    /**
     * 指定された ID のユーザー情報を更新する
     * 
     * @param userId ユーザーの ID
     * @param req 更新リクエスト（名前・プロフィール）
     * @return 更新後のユーザー情報
     * @throws UserException ユーザーが見つからない場合に発生
     */
    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        // ユーザーを取得（見つからなければ例外）
        User user = this.findUserById(userId);

        // 更新データがある場合のみセット
        if (req.getName() != null) {
            user.setName(req.getName());
        }
        if (req.getProfile() != null) {
            user.setProfile(req.getProfile());
        }

        // 更新後のユーザー情報を保存
        return this.userRepository.save(user);
    }

    /**
     * 名前またはメールアドレスでユーザーを検索する
     * 
     * @param query 検索ワード
     * @return 検索結果のユーザーリスト
     */
    @Override
    public List<User> searchUser(String query) {
        return this.userRepository.searchUser(query);
    }

}

