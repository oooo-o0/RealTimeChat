package com.example.app.exception;

/**
 * チャット関連の例外を扱うカスタム例外クラス
 */
public class ChatException extends Exception {

    /**
     * コンストラクタ：エラーメッセージを受け取る
     * @param message エラーメッセージ
     */
    public ChatException(String message) {
        super(message);
    }
}
