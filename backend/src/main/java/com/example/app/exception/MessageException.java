package com.example.app.exception;

/**
 * メッセージ関連のエラーを処理するためのカスタム例外クラス
 * - メッセージ送信・取得などで発生するエラーを管理
 */
public class MessageException extends Exception { 

    /**
     * メッセージ関連の例外を発生させる
     * @param message エラーメッセージ
     */
    public MessageException(String message) {
        super(message);
    }
}

