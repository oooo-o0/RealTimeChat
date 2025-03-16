package com.example.app.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT（JSON Web Token）の生成・解析を行うクラス
 * - 認証情報を元にトークンを作成
 * - トークンからユーザー情報を取得
 */
@Service
public class TokenProvider {

    // シークレットキー（トークンの署名に使用）
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    /**
     * 認証情報を元に JWT を生成する
     * 
     * @param authentication 認証情報（Spring Security の Authentication）
     * @return 生成された JWT（文字列）
     */
    public String generateToken(Authentication authentication) {

        // JWT の作成
        String jwt = Jwts.builder()
                .setIssuer("Dipen") // 発行者を設定
                .setIssuedAt(new Date()) // 発行日時を設定
                .setExpiration(new Date(new Date().getTime() + 86400000)) // 有効期限（1日）
                .claim("email", authentication.getName()) // ユーザー情報を追加
                .signWith(key) // 署名
                .compact();

        return jwt;
    }

    /**
     * JWT からユーザーのメールアドレスを取得する
     * 
     * @param jwt JWT トークン
     * @return トークン内のメールアドレス
     */
    public String getEmailFromToken(String jwt) {
        // "Bearer " を取り除く（最初の 7 文字をカット）
        jwt = jwt.substring(7);

        // トークンを解析してクレームを取得
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        // "email" クレームからメールアドレスを取得
        String email = String.valueOf(claim.get("email"));
        return email;
    }
}

