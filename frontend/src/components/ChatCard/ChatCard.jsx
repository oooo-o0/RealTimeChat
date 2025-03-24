import React from "react";

const ChatCard = ({ userImg, name, lastMessage }) => {
  // タイムスタンプを読みやすい日付形式にフォーマットする関数
  const formatTimestamp = (timestamp) => {
    // `timestamp` が存在しない場合は空文字を返す
    if (!timestamp) return "";

    // 日付フォーマットのオプション（例: "Mar 24, 2025" のような表示）
    const options = { year: "numeric", month: "short", day: "numeric" };

    // `toLocaleDateString` を使用して、ローカルの日時表記に変換
    return new Date(timestamp).toLocaleDateString(undefined, options);
  };

  return (
    <div className="flex items-center justify-center py-2 group cursor-pointer">
      {/* ユーザーのプロフィール画像 */}
      <div className="w-[19%]">
        <img className="h-13 w-14 rounded-full" src={userImg} alt="profile" />
      </div>

      {/* チャットの情報（名前、最後のメッセージ） */}
      <div className="pl-5 w-[80%]">
        {/* ユーザー名と最終メッセージのタイムスタンプ */}
        <div className="flex justify-between items-center">
          <p className="text-lg">{name}</p>
          {/* `lastMessage` がある場合のみ、タイムスタンプを表示 */}
          <p className="text-sm">{lastMessage ? formatTimestamp(lastMessage.timestamp) : ""}</p>
        </div>

        {/* 最終メッセージの内容と未読メッセージの表示 */}
        <div className="flex justify-between items-center">
          {/* 最終メッセージの内容（長すぎる場合は `truncate` で省略） */}
          <p className="text-gray-600 truncate">{lastMessage ? lastMessage.content : ""}</p>

          {/* メッセージの時間と未読メッセージのアイコン */}
          <div className="flex space-x-2">
            {/* メッセージが送信されてからの経過時間（仮に3時間と表示） */}
            <span className="text-gray-500 text-xs">3h</span>
            {/* 未読メッセージがある場合の通知アイコン（緑のドット） */}
            <span className="bg-green-500 h-2 w-2 rounded-full"></span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatCard;
