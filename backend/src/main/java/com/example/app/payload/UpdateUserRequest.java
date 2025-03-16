package com.example.app.payload;

/**
 * ユーザー情報更新用のリクエストデータを管理するクラス
 * - ユーザーの名前やプロフィール画像を更新するために使用
 */
public class UpdateUserRequest {

    /** 更新するユーザー名 */
    private String name;

    /** 更新するプロフィール画像（URLまたはデータ） */
    private String profile;

    // Getter（取得用メソッド）
    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    // Setter（設定用メソッド）
    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    /** デフォルトコンストラクタ（空のインスタンスを作成可能にする） */
    public UpdateUserRequest() {
    }

    /**
     * コンストラクタ（フィールドを初期化）
     * @param name 更新するユーザー名
     * @param profile 更新するプロフィール画像
     */
    public UpdateUserRequest(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }

    /**
     * オブジェクトの文字列表現を返す
     * @return name と profile を含む文字列
     */
    @Override
    public String toString() {
        return "UpdateUserRequest [name=" + name + ", profile=" + profile + "]";
    }
}

