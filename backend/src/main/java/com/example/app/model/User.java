package com.example.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ユーザー情報を表すエンティティクラス
 * - データベースの `user` テーブルと対応
 */
@Entity // JPAエンティティ（データベースのテーブルとマッピング）
@Table(name = "User") // テーブル名を明示的に指定
public class User {

    @Id // 主キー（PK）
    @GeneratedValue(strategy = GenerationType.AUTO) // IDを自動生成
    private int id;

    private String name;      // ユーザー名
    private String email;     // メールアドレス（ログイン時に使用）
    private String profile;   // プロフィール情報（画像URLなど）
    private String password;  // ハッシュ化されたパスワード

    // === Getter & Setter ===
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // === コンストラクタ ===
    
    // デフォルトコンストラクタ（JPAで必須）
    public User() {
    }

    // すべてのフィールドを含むコンストラクタ
    public User(int id, String name, String email, String profile, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    // オブジェクト情報を文字列として取得（デバッグ用）
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", profile=" + profile + ", password="
                + password + "]";
    }

}

