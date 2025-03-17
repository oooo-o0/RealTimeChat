package com.example.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.app.model.Message;

/**
 * WebSocket を利用したリアルタイムチャットのコントローラー。
 */
public class RealTimeChat {

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * クライアントからのメッセージを受信し、全体にブロードキャストする。
     *
     * @param message 受信したメッセージ
     * @return 送信されたメッセージ
     */
    @MessageMapping("/message") // クライアントは "/app/message" にメッセージを送信
    @SendTo("/group/public")  // すべてのクライアントが "/group/public" を購読していると受信できる
    public Message recieveMessage(@Payload Message message) {
        // 特定のチャットルーム（グループ）へメッセージを送信
        simpMessagingTemplate.convertAndSend("/group" + message.getChat().getId().toString(), message);
        return message;
    }
}