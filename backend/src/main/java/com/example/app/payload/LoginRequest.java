package com.example.app.payload;

public class LoginRequest { 

    private String email;      // ユーザーのメールアドレス
    private String password;   // ユーザーのパスワード

    // email を取得
    public String getEmail() {
        return email;
    }

    // email を設定
    public void setEmail(String email) {
        this.email = email;
    }

    // password を取得
    public String getPassword() {
        return password;
    }

    // password を設定
    public void setPassword(String password) {
        this.password = password;
    }

    // デフォルトコンストラクタ
    public LoginRequest() {
    }

    // email と password を受け取るコンストラクタ
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // toString メソッドのオーバーライド（ログ用）
    @Override
    public String toString() {
        return "LoginRequest [email=" + email + ", password=" + password + "]";
    }
}

