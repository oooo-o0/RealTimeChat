package com.example.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.model.Message;

/**
 * MessageRepository は、メッセージに関連するデータ操作を行うリポジトリインターフェース。
 * JpaRepository を継承することで、基本的な CRUD 操作を利用できる。
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * 特定のチャット（chatId）に関連するすべてのメッセージを取得するカスタムクエリ。
     * 
     * @param chatId 取得したいメッセージが属するチャットの ID
     * @return 指定されたチャット ID に関連するメッセージのリスト
     */
    @Query("select m from Message m join m.chat c where c.id=:chatId")
    public List<Message> findByChatId(@Param("chatId") Integer chatId);
}

