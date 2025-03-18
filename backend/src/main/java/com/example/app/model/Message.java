package com.example.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * メッセージのエンティティ（チャット内の各メッセージを表現）
 */
@Entity
@Table(name = "Message") // テーブル名を明示的に指定
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // メッセージのユニークID

    private String content; // メッセージ内容
    private LocalDateTime timestamp; // 送信日時

    @ManyToOne
    private Chat chat; // どのチャットに属するメッセージか

    @ManyToOne
    private User user; // 送信者

    /** コンストラクタ */
    public Message() {}

    public Message(Integer id, String content, LocalDateTime timestamp, Chat chat, User user) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.chat = chat;
        this.user = user;
    }

    /** Getter & Setter */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", content=" + content + ", timestamp=" + timestamp + ", chat=" + chat + ", user=" + user + "]";
    }
}
