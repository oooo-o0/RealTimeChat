package com.example.app.payload;

/**
 * 1対1チャットのリクエストを受け取るDTO（データ転送オブジェクト）
 */
public class SingleChatRequest {

    /** チャット相手のユーザーID */
    private Integer userId;

    // ---- コンストラクタ ----
    
    /**
     * デフォルトコンストラクタ
     */
    public SingleChatRequest() {
    }

    /**
     * ユーザーIDを指定するコンストラクタ
     * @param userId チャット相手のユーザーID
     */
    public SingleChatRequest(Integer userId) {
        this.userId = userId;
    }

    // ---- ゲッター & セッター ----

    /**
     * ユーザーIDを取得
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * ユーザーIDを設定
     * @param userId 設定するユーザーID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // ---- デバッグ用 ----
    
    /**
     * オブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return "SingleChatRequest [userId=" + userId + "]";
    }

}

