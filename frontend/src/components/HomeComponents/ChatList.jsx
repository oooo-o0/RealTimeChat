import React from "react";
import ChatCard from "../ChatCard/ChatCard";

const ChatList = ({ querys, auth, chat, lastMessages, handleClickOnChatCard, handleCurrentChat }) => {
  return (
    <div className="bg-white overflow-y-scroll h-[73vh] px-3">
      {/* 検索結果がある場合は検索結果のリストを表示 */}
      {querys &&
        auth.searchUser?.map((item, index) => (
          <div key={index} onClick={() => handleClickOnChatCard(item.id)}>
            <hr /> {/* 各チャットの区切り線 */}
            <ChatCard
              name={item.name} // ユーザー名を表示
              userImg={item.profile || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp?b=1&s=170667a&w=0&k=20&c=wpJ0QJYXdbLx24H5LK08xSgiQ3zNkCAD2W3F74qlUL0="} // プロフィール画像がない場合はデフォルト画像を使用
              lastMessage={{
                content: lastMessages[item.id]?.content || "Start your conversation", // 最新メッセージがある場合は表示、ない場合はデフォルトテキスト
                timestamp: lastMessages[item.id]?.timestamp || "", // メッセージのタイムスタンプ
              }}
            />
          </div>
        ))}

      {/* 通常のチャットリストの表示（検索が行われていない場合） */}
      {chat?.chats?.length > 0 &&
        !querys &&
        chat?.chats?.map((item, index) => (
          <div
            key={index}
            onClick={() => {
              handleCurrentChat(item); // 選択したチャットを現在のチャットに設定
              console.log("the item is ", item); // 選択したチャットのデータをコンソールに出力
            }}
          >
            <hr /> {/* 各チャットの区切り線 */}
            <ChatCard
              isChat={!item.group} // グループチャットでない場合は true
              name={
                item.group
                  ? item.chatName // グループチャットの場合はグループ名を表示
                  : auth.reqUser?.id !== item.users[0]?.id
                  ? item.users[0]?.name // ログインユーザーが `users[0]` でない場合、そのユーザー名を表示
                  : item.users[1]?.name // それ以外は `users[1]` のユーザー名を表示
              }
              userImg={item.chatImage || "https://media.istockphoto.com/id/1455296779/photo/smiling-businesspeople-standing-arm-in-arm-in-an-office-hall.webp?b=1&s=170667a&w=0&k=20&c=0bdu3-mVcOw6FN_vIkwTx4pCE6jgL7Jy29bBWZhoiik="} // チャット画像がない場合はデフォルト画像を使用
              lastMessage={{
                content: lastMessages[item.id]?.content || "Start your conversation", // 最新メッセージがある場合は表示、ない場合はデフォルトテキスト
                timestamp: lastMessages[item.id]?.timestamp || "", // メッセージのタイムスタンプ
              }}
            />
          </div>
        ))}
    </div>
  );
};

export default ChatList;
