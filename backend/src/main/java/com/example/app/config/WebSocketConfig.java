package com.example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket の設定クラス。
 * STOMP プロトコルを使用したリアルタイムメッセージングを構成する。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * クライアントが接続する WebSocket エンドポイントを登録。
     * SockJS を有効にすることで、WebSocket 非対応の環境でも動作可能にする。
     * 
     * @param registry STOMP エンドポイントの登録オブジェクト
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // WebSocket のエンドポイント
                .setAllowedOrigins("*")  // CORS 設定（すべてのオリジンを許可）
                .withSockJS();  // SockJS を有効にする
    }

    /**
     * メッセージブローカーの設定を行う。
     * - `/app` プレフィックスのメッセージはアプリケーション側のコントローラーが処理。
     * - `/group` および `/user` プレフィックスのメッセージはクライアントに直接ブロードキャスト。
     * - `/user` プレフィックスは特定のユーザーへのプライベートメッセージングに使用。
     * 
     * @param registry メッセージブローカーの登録オブジェクト
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");  // クライアントからの送信先プレフィックス
        registry.enableSimpleBroker("/group", "/user");  // クライアントへ送信するためのシンプルブローカー
        registry.setUserDestinationPrefix("/user");  // ユーザーごとのプライベートメッセージのプレフィックス
    }
}

