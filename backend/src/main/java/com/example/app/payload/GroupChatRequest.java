package com.example.app.payload;

import java.util.List;

/**
 * グループチャット作成リクエストのペイロード（DTO）
 */
public class GroupChatRequest {

    private List<Integer> userIds; // 参加するユーザーのIDリスト
    private String chatName;       // グループの名前
    private String chatImage;      // グループの画像（URLなど）

    // ---- Getter & Setter ----
    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
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

    // ---- コンストラクタ ----
    public GroupChatRequest() {
    }

    public GroupChatRequest(List<Integer> userIds, String chatName, String chatImage) {
        this.userIds = userIds;
        this.chatName = chatName;
        this.chatImage = chatImage;
    }

    @Override
    public String toString() {
        return "GroupChatRequest [userIds=" + userIds + ", chatName=" + chatName + ", chatImage=" + chatImage + "]";
    }
}

