package com.example.app.payload;

/**
 * API のレスポンスを統一するためのクラス
 * - 成功・失敗のメッセージを格納
 * - ステータス（true: 成功, false: 失敗）を格納
 */
public class ApiResponse {

    private String message;  // API のレスポンスメッセージ
    private boolean status;  // 成功・失敗のステータス

    /**
     * メッセージを取得
     * @return メッセージ文字列
     */
    public String getMessage() {
        return message;
    }

    /**
     * メッセージを設定
     * @param message 設定するメッセージ
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ステータスを取得
     * @return true: 成功, false: 失敗
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * ステータスを設定
     * @param status 設定するステータス（true: 成功, false: 失敗）
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * デフォルトコンストラクタ
     */
    public ApiResponse() {
    }

    /**
     * メッセージとステータスを指定するコンストラクタ
     * @param message レスポンスメッセージ
     * @param status ステータス（true: 成功, false: 失敗）
     */
    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    /**
     * オブジェクトの文字列表現を返す
     */
    @Override
    public String toString() {
        return "ApiResponse [message=" + message + ", status=" + status + "]";
    }
}

