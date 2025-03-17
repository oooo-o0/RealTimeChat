package com.example.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.model.Chat;
import com.example.app.model.User;


/**
 * Chat エンティティのデータ操作を提供するリポジトリ
 */
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    /**
     * 指定したユーザーIDが参加しているすべてのチャットを取得する
     */
    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    public List<Chat> findChatByUserId(@Param("userId") Integer userId);

    /**
     * 2人のユーザーが参加している「個別チャット」を取得する
     * 条件：
     *  - isGroup=false（グループチャットでない）
     *  - 指定された2人のユーザーが members に含まれている
     */
    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :user MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    public Chat findSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);
}
