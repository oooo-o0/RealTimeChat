package com.example.app.config;
/**
 * JWTの設定値を管理するクラス
 * - アプリケーション全体で共通のJWT設定を使用可能にする
 * - JWTのヘッダー名やシークレットキーを定数として定義
 * - 他のクラスから参照しやすくするため、static finalで宣言
 */
public class JwtConstant {

    /** 
     * クライアントから送信されるJWTのヘッダー名 （"Authorization"ヘッダーを指定）
     * - 一般的に "Authorization" ヘッダーにトークンを含める
     * - 例: Authorization: Bearer <JWT>
     */
    public static final String JWT_HEADER = "Authorization";

    /**
     * JWTの署名に使用するシークレットキー（HMAC用）
     * - JWTの署名を検証し、改ざんされていないか確認するために使用
     * - HMACアルゴリズムで署名する場合に必要（十分な長さが推奨される）
     * ※ 実際の運用では環境変数やプロパティファイルに格納し、直書きは避けるべき
     */
    public static final String SECRET_KEY = "jkahlfadskjahfgfadsfadafdsgfdvresdfpiurtharklarjwroajfn,msdfhjaio;fdjasflkjaf;lkdhjfaf";

}
