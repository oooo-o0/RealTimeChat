package com.example.app.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWTを検証し、認証情報をSpring Securityのコンテキストに設定するフィルター
 * - すべてのリクエストに対して1回だけ実行される（OncePerRequestFilterを継承）
 */
public class JwtValidator extends OncePerRequestFilter {

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // リクエストヘッダーからJWTを取得
        String jwt = request.getHeader("Authorization");

        if (jwt != null) {
            try {
                // "Bearer " のプレフィックスを削除
                jwt = jwt.substring(7);

                // シークレットキーを取得し、JWTを復号・検証
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                // クレーム（JWTのペイロード）からユーザー情報を取得
                String username = String.valueOf(claim.get("email")); // ユーザー識別情報（メールアドレス）
                String authorities = String.valueOf(claim.get("authorities")); // ユーザー権限

                // 文字列の権限情報をGrantedAuthorityリストに変換
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // 認証情報を作成（パスワードは不要）
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, auths);

                // 認証情報をSpring Securityのコンテキストに保存（ログイン状態を維持）
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // トークンの検証に失敗した場合、不正な認証情報エラーをスロー
                throw new BadCredentialsException("Invalid token received...");
            }
        }

        // 次のフィルターに処理を渡す
        filterChain.doFilter(request, response);
    }
}

