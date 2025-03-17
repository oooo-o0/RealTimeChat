package com.example.app.payload;

public class AuthResponse {

    private String jwt;  // 発行されたJWTトークン
    private boolean isAuth;  // 認証成功フラグ

    // JWTを取得
    public String getJwt() {
        return jwt;
    }

    // JWTを設定
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    // 認証成功フラグを取得
    public boolean isAuth() {
        return isAuth;
    }

    // 認証成功フラグを設定
    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    // デフォルトコンストラクタ
    public AuthResponse() {
    }

    // JWTと認証状態をセットするコンストラクタ
    public AuthResponse(String jwt, boolean isAuth) {
        this.jwt = jwt;
        this.isAuth = isAuth;
    }

    // toString メソッドのオーバーライド（ログ用）
    @Override
    public String toString() {
        return "AuthResponse [jwt=" + jwt + ", isAuth=" + isAuth + "]";
    }
}
