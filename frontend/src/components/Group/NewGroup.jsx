import { Avatar, Button, CircularProgress } from "@mui/material";
import { useState } from "react";
import { BsArrowLeft, BsCheck2 } from "react-icons/bs";
import { useDispatch } from "react-redux";
import { createGroupChat } from "../../Redux/Chat/Action";

const NewGroup = ({ groupMember, setIsGroup }) => {
  const [isImageUploading, setIsImageUploading] = useState(false); // 画像アップロード中かどうかを管理するステート

  const [groupImage, setGroupImage] = useState(null); // グループ画像のURLを保存するステート

  const [groupName, setGroupName] = useState(""); // グループ名を保存するステート

  const dispatch = useDispatch(); // Reduxのdispatch関数を取得

  const token = localStorage.getItem("token"); // ローカルストレージからユーザーのトークンを取得

  // グループ作成処理
  const handleCreateGroup = () => {
    // メンバーのIDを取得
    let userIds = [];
    for (let user of groupMember) {
      userIds.push(user.id);
    }

    // グループのオブジェクトを作成
    const group = {
      userIds,
      chatName: groupName,
      chatImage: groupImage,
    };

    // APIリクエストに必要なデータを作成
    const data = {
      group,
      token,
    };

    // Reduxのアクションをディスパッチしてグループチャットを作成
    dispatch(createGroupChat(data));

    // グループ作成UIを閉じる
    setIsGroup(false);
  };

  // 画像をCloudinaryにアップロードする関数
  const uploadToCloudinary = (pics) => {
    // アップロード中フラグをtrueに設定
    setIsImageUploading(true);

    // FormDataオブジェクトを作成し、ファイル情報を追加
    const data = new FormData();
    data.append("file", pics);
    data.append("upload_preset", "whatsapp"); // Cloudinaryの設定
    data.append("cloud_name", "dadlxgune");

    // CloudinaryのAPIへ画像をアップロード
    fetch("https://api.cloudinary.com/v1_1/dadlxgune/image/upload", {
      method: "post",
      body: data,
    })
      .then((res) => res.json()) // レスポンスをJSONに変換
      .then((data) => {
        // アップロードされた画像のURLをステートに保存
        setGroupImage(data.url.toString());
        // アップロード完了フラグをfalseに設定
        setIsImageUploading(false);
      });
  };

  return (
    <div className="w-full h-full">
      {/* ヘッダー */}
      <div className="flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5">
        {/* 戻るボタン（左矢印アイコン） */}
        <BsArrowLeft className="cursor-pointer text-2xl font-bold" />
        <p className="text-xl font-semibold">New Group</p>
      </div>

      {/* グループ画像アップロードセクション */}
      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput" className="relative">
          {/* アバター画像を表示（デフォルト画像あり） */}
          <Avatar alt="Group Image" sx={{ width: "15rem", height: "15rem" }} src={groupImage || "https://media.istockphoto.com/id/1455296779/photo/smiling-businesspeople-standing-arm-in-arm-in-an-office-hall.webp?b=1&s=170667a&w=0&k=20&c=0bdu3-mVcOw6FN_vIkwTx4pCE6jgL7Jy29bBWZhoiik="} />
          {/* アップロード中はローディングインジケーターを表示 */}
          {isImageUploading && <CircularProgress className="absolute top-[5rem] left-[6rem]" />}
        </label>

        {/* 画像選択用の非表示の input */}
        <input type="file" id="imgInput" className="hidden" onChange={(e) => uploadToCloudinary(e.target.files[0])} />
      </div>

      {/* グループ名入力フィールド */}
      <div className="w-full flex justify-between items-center py-2 px-5">
        <input className="w-full outline-none border-b-2 border-green-700 px-2 bg-transparent" placeholder="Group Subject" value={groupName || ""} type="text" onChange={(e) => setGroupName(e.target.value)} />
      </div>

      {/* グループ作成ボタン（グループ名が入力されている場合のみ表示） */}
      {groupName && (
        <div className="py-10 bg-slate-200 flex items-center justify-center">
          <Button onClick={handleCreateGroup}>
            <div className="bg-[#0c977d] rounded-full p-4">
              <BsCheck2 className="text-white text-3xl font-bold" />
            </div>
          </Button>
        </div>
      )}
    </div>
  );
};

export default NewGroup;
