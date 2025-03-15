package com.example.app.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration // このクラスをSpringの設定クラスとして認識させる
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // セッション管理の設定（ステートレス: JWTなどのトークンベース認証向け）
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 認可設定
            .authorizeHttpRequests(authorize -> 
                authorize
                    // "/api/**" のエンドポイントは認証が必要（ログインユーザーのみアクセス可能）
                    .requestMatchers("/api/**").authenticated()
                    // それ以外のリクエストは認証不要（全ユーザーアクセス可能）
                    .anyRequest().permitAll()
            )

            // JWT検証用のフィルターを追加（BasicAuthenticationFilterの前に実行）
            .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)

            // CSRF（クロスサイトリクエストフォージェリ）対策を無効化（JWT認証のため不要）
            .csrf(csrf -> csrf.disable())

            // CORS（クロスオリジンリソースシェアリング）の設定
            .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                @SuppressWarnings("null")
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration cfg = new CorsConfiguration();

                    // 許可するオリジン（フロントエンドのURL）
                    cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                    // 許可するオリジンパターン
                    cfg.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));

                    // 許可するHTTPメソッド（全て許可）
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    // 許可するHTTPヘッダー（全て許可）
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    // クライアントがアクセス可能なレスポンスヘッダー（Authorizationヘッダーを許可）
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));
                    // CORS設定のキャッシュ時間（1時間）
                    cfg.setMaxAge(3600L);

                    return cfg;
                }
            }))

            // フォームログインの有効化（デフォルト設定）
            .formLogin(Customizer.withDefaults())
            // HTTP Basic認証の有効化（デフォルト設定）
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // パスワードを安全にハッシュ化するためのエンコーダー（BCryptを使用）
        return new BCryptPasswordEncoder();
    }
}

