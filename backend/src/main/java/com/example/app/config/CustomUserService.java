package com.example.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;


@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // ユーザー情報を取得するリポジトリ

    /**
     * メールアドレス（username）を元にユーザー情報を取得し、
     * Spring Security 用の `UserDetails` に変換して返す
     *
     * @param username メールアドレス
     * @return UserDetailsオブジェクト
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // データベースからユーザー情報を取得
        User user = this.userRepository.findByEmail(username);

        // ユーザーが見つからない場合は例外をスロー
        if (user == null) {
            throw new UsernameNotFoundException("User not found with provided username: " + username);
        }

        // ユーザーの権限リストを作成（今回は空）
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Spring Security の `UserDetails` を作成して返す
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), 
            user.getPassword(), 
            authorities
        );
    }
}
