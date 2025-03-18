package com.example.app.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Chat") // テーブル名を明示的に指定
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // チャットのユニークID

    private String chatName; // チャット名（グループのみ）
    private String chatImage; // チャットアイコンのURL
    private boolean isGroup; // グループチャットかどうか

    // 💡 ManyToMany の中間テーブルを明示的に指定
    @ManyToMany
    @JoinTable(
        name = "Chat_Admins",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> admins = new HashSet<>(); // グループの管理者（グループのみ）

    @ManyToOne
    @JoinColumn(name = "created_by") // 外部キーを明示的に指定
    private User createdBy; // チャット作成者

    // 💡 ManyToMany の中間テーブルを明示的に指定
    @ManyToMany
    @JoinTable(
        name = "Chat_Users",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>(); // 参加しているユーザー

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>(); // チャット内のメッセージ一覧

    /** コンストラクタ */
    public Chat() {}

    public Chat(Integer id, String chatName, String chatImage, boolean isGroup, Set<User> admins, User createdBy,
            Set<User> users, List<Message> messages) {
        this.id = id;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.isGroup = isGroup;
        this.admins = admins;
        this.createdBy = createdBy;
        this.users = users;
        this.messages = messages;
    }

    /** Getter & Setter */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + ", chatName=" + chatName + ", chatImage=" + chatImage + ", isGroup=" + isGroup
                + ", admins=" + admins + ", createdBy=" + createdBy + ", users=" + users + ", messages=" + messages
                + "]";
    }
}


