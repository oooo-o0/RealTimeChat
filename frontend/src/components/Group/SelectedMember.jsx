import React from "react";
import { AiOutlineClose } from "react-icons/ai"; // React Iconsライブラリから`AiOutlineClose`アイコンをインポート

// 選択されたメンバーを表示するコンポーネント
const SelectedMember = ({ handleRemoveMember, member }) => {
  return (
    <div className="flex items-center bg-slate-300 rounded-full p-1">
      {/* メンバーのプロフィール画像を表示 */}
      <img
        className="w-7 h-7 rounded-full"
        src={
          member.profile || // メンバーのプロフィール画像がある場合はそれを使用
          "https://cdn.pixabay.com/photo/2024/07/29/21/17/profile-8930641_640.png" // プロフィール画像がない場合のデフォルト画像
        }
        alt="profile"
      />

      {/* メンバー名を表示 */}
      <p className="px-2 text-white">{member.name}</p>

      {/* メンバーを削除するための閉じるボタン（アイコン） */}
      <AiOutlineClose
        onClick={() => handleRemoveMember(member)} // クリックするとメンバーを削除
        className="cursor-pointer text-white"
      />
    </div>
  );
};

export default SelectedMember;
