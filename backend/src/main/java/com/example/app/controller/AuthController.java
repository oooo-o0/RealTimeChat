package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.config.CustomUserService;
import com.example.app.config.TokenProvider;
import com.example.app.exception.UserException;
import com.example.app.model.User;
import com.example.app.payload.AuthResponse;
import com.example.app.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository; // ユーザー情報の保存・取得

    @Autowired
    private PasswordEncoder passwordEncoder; // パスワードのハッシュ化

    @Autowired
    private TokenProvider tokenProvider; // JWT トークンの生成・検証

    @Autowired
    private CustomUserService customUserService; // ユーザーロードサービス

    /**
     * ユーザー新規登録（サインアップ）
     * @param user ユーザー情報（リクエストボディ）
     * @return 認証トークン (JWT) を含むレスポンス
     * @throws UserException メールアドレスが既に使用されている場合
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();

        // 既にメールアドレスが登録されているか確認
        User isUser = this.userRepository.findByEmail(email);
        if (isUser != null) {
            throw new UserException("Email is used with another account");
        }

        // 新しいユーザーを作成し、パスワードをハッシュ化して保存
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setName(name);
        createdUser.setPassword(this.passwordEncoder.encode(password));

        userRepository.save(createdUser); // データベースに保存

        // 認証処理（ログイン状態を保持）
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT を発行
        String jwt = this.tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(jwt, true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * ユーザーログイン（サインイン）
     * @param request ログインリクエスト（メール・パスワード）
     * @return 認証トークン (JWT) を含むレスポンス
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // 認証処理
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT を発行
        String jwt = this.tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(jwt, true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * ユーザー認証処理（認証情報の作成）
     * @param username ユーザー名（メールアドレス）
     * @param password パスワード
     * @return 認証トークン
     * @throws BadCredentialsException 認証失敗時の例外
     */
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.customUserService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        // 入力されたパスワードとデータベースに保存されているハッシュ化されたパスワードを比較
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password or username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

