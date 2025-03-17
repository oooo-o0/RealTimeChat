package com.example.app.payload;

import java.time.LocalDateTime;

/**
 * エラーメッセージの詳細情報を保持するデータモデル
 * - 例外発生時に、クライアントにエラー情報を提供
 */
public class ErrorDetail {

    private String error;  // エラーの種類や概要
    private String message;  // 詳細なエラーメッセージ
    private LocalDateTime timeStamp;  // エラー発生時刻

    // ======== Getter & Setter ========

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    // ======== コンストラクタ ========

    /**
     * デフォルトコンストラクタ（空のオブジェクト作成用）
     */
    public ErrorDetail() {
    }

    /**
     * エラー情報を設定するコンストラクタ
     * @param error エラーの種類
     * @param message 詳細なエラーメッセージ
     * @param timeStamp エラー発生時刻
     */
    public ErrorDetail(String error, String message, LocalDateTime timeStamp) {
        this.error = error;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ErrorDetail [error=" + error + ", message=" + message + ", timeStamp=" + timeStamp + "]";
    }

}

