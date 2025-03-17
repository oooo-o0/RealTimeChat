package com.example.app.payload;
/**
 * SendMessageRequest クラスは、メッセージ送信時のリクエストデータを表す。
 * ユーザー ID、チャット ID、メッセージ内容を含む。
 */
public class SendMessageRequest {

    // メッセージを送信するユーザーの ID
    private Integer userId;

    // メッセージを送信するチャットの ID
    private Integer chatId;

    // メッセージの内容
    private String content;

    // ユーザー ID を取得
    public Integer getUserId() {
        return userId;
    }

    // ユーザー ID を設定
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // チャット ID を取得
    public Integer getChatId() {
        return chatId;
    }

    // チャット ID を設定
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    // メッセージ内容を取得
    public String getContent() {
        return content;
    }

    // メッセージ内容を設定
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * デフォルトコンストラクタ（引数なし）
     * オブジェクトの初期化のために使用
     */
    public SendMessageRequest() {
    }

    /**
     * パラメータ付きコンストラクタ
     * 
     * @param userId メッセージを送信するユーザー ID
     * @param chatId メッセージを送信するチャット ID
     * @param content メッセージの内容
     */
    public SendMessageRequest(Integer userId, Integer chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }

    /**
     * オブジェクトの文字列表現を返す（ログ出力やデバッグ用）
     */
    @Override
    public String toString() {
        return "SendMessageRequest [userId=" + userId + ", chatId=" + chatId + ", content=" + content + "]";
    }

}
