package com.example.app.exception;
/**
 * ユーザー関連のエラーを処理するためのカスタム例外クラス
 * - 例外メッセージを受け取り、親クラス Exception に渡す
 */
public class UserException extends Exception {
	/**
     * コンストラクタ（エラーメッセージを受け取る）
     * @param message 発生したエラーの詳細メッセージ
     */
    public UserException(String message) {
        super(message);
    }
}
