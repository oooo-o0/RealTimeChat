package com.example.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.model.User;

/**
 * Userエンティティのデータベース操作を行うリポジトリインターフェース
 * - JpaRepositoryを継承し、CRUD機能を提供
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	
    /**
     * メールアドレスからユーザーを検索
     * - メールアドレスは一意のため、1件のみ取得
     * 
     * @param email 検索するメールアドレス
     * @return 該当するユーザー（存在しない場合は null）
     */
    public User findByEmail(String email);

    /**
     * 名前またはメールアドレスに特定の文字列を含むユーザーを検索
     * - `like %:query%` により部分一致検索を実施
     * 
     * @param query 検索ワード
     * @return 一致するユーザーのリスト
     */
    @Query("select u from User u where u.name like %:query% or u.email like %:query%")
    public List<User> searchUser(@Param("query") String query);

}
