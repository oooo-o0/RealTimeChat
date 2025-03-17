package com.example.app.service;

/**
 * チャットに関するサービスのインターフェース
 */
public interface ChatService {
	 /**
     * 1対1のチャットを作成
     * @param reqUser チャットを開始するユーザー
     * @param userId 相手のユーザーID
     * @return 作成されたチャット
     * @throws UserException ユーザーが見つからない場合
     */
    public Chat createChat(User reqUser, Integer userId) throws UserException;

    /**
     * チャットIDを指定してチャットを取得
     * @param chatId 取得するチャットのID
     * @return チャット情報
     * @throws ChatException チャットが見つからない場合
     */
    public Chat findChatById(Integer chatId) throws ChatException;

    /**
     * ユーザーのすべてのチャットを取得
     * @param userId ユーザーID
     * @return チャットリスト
     * @throws UserException ユーザーが見つからない場合
     */
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    /**
     * グループチャットを作成
     * @param req グループの情報（グループ名、参加ユーザーなど）
     * @param reqUser 作成者
     * @return 作成されたグループチャット
     * @throws UserException ユーザーが見つからない場合
     */
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

    /**
     * グループにユーザーを追加
     * @param userId 追加するユーザーのID
     * @param chatId グループチャットのID
     * @param reqUser 操作を行うユーザー
     * @return 更新されたグループチャット
     * @throws UserException ユーザーが見つからない場合
     * @throws ChatException チャットが見つからない場合
     */
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;

    /**
     * グループの名前を変更
     * @param chatId グループチャットのID
     * @param groupName 新しいグループ名
     * @param reqUser 操作を行うユーザー
     * @return 更新されたグループチャット
     * @throws ChatException チャットが見つからない場合
     * @throws UserException ユーザーが権限を持たない場合
     */
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException;

    /**
     * グループからユーザーを削除
     * @param chatId グループチャットのID
     * @param userId 削除するユーザーのID
     * @param reqUser 操作を行うユーザー
     * @return 更新されたグループチャット
     * @throws UserException ユーザーが見つからない場合
     * @throws ChatException チャットが見つからない場合
     */
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;

    /**
     * チャットを削除
     * @param chatId 削除するチャットのID
     * @param userId 操作を行うユーザーID
     * @throws ChatException チャットが見つからない場合
     * @throws UserException ユーザーが権限を持たない場合
     */
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;
}
}
