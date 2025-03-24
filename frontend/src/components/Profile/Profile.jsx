import { useState } from "react";
import { BsArrowLeft, BsCheck2, BsPencil } from "react-icons/bs";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateUser } from "../../Redux/Auth/Action";

const Profile = ({ handleCloseOpenProfile }) => {
  const [flag, setFlag] = useState(false); // プロフィール編集フラグの状態を管理
  const [username, setUsername] = useState(null); // ユーザー名の状態を管理
  const [tempPicture, setTempPicture] = useState(null); // 一時的なプロフィール画像の状態を管理
  const { auth } = useSelector((store) => store); // グローバル状態（認証情報）を取得
  const dispatch = useDispatch(); // Reduxのdispatch関数を取得

  // フラグ状態を true に設定する関数（編集モードを有効にする）
  const handleFlag = () => {
    setFlag(true);
  };

  // 「チェック」ボタンがクリックされたときに名前を更新する関数
  const handleCheckClick = (e) => {
    setFlag(false); // 編集モードを終了
    const data = {
      id: auth.reqUser.id, // 現在のユーザーの ID
      token: localStorage.getItem("token"), // ローカルストレージからトークンを取得
      data: { name: username }, // 更新するユーザー名
    };
    dispatch(updateUser(data)); // ユーザー情報を更新
  };

  // 入力フォームの変更をハンドリングする関数（ユーザー名の入力）
  const handleChange = (e) => {
    setUsername(e.target.value);
  };

  // Cloudinary に画像をアップロードする関数
  const uploadToCloudinary = (pics) => {
    const data = new FormData();
    data.append("file", pics); // 選択した画像ファイルを追加
    data.append("upload_preset", "whatsapp"); // Cloudinary のアップロードプリセット
    data.append("cloud_name", "dadlxgune"); // Cloudinary のクラウド名

    fetch("https://api.cloudinary.com/v1_1/dadlxgune/image/upload", {
      method: "post",
      body: data,
    })
      .then((res) => res.json())
      .then((data) => {
        setTempPicture(data.url.toString()); // 一時的なプロフィール画像を更新
        const dataa = {
          id: auth.reqUser.id, // 現在のユーザーの ID
          token: localStorage.getItem("token"), // ローカルストレージからトークンを取得
          data: { profile: data.url.toString() }, // アップロードした画像の URL を格納
        };
        dispatch(updateUser(dataa)); // ユーザー情報を更新
      });
  };

  // Enter キーが押されたときに名前を更新する関数
  const handleUpdateName = (e) => {
    if (e.key === "Enter") {
      const data = {
        id: auth.reqUser.id, // 現在のユーザーの ID
        token: localStorage.getItem("token"), // ローカルストレージからトークンを取得
        data: { name: username }, // 更新するユーザー名
      };
      dispatch(updateUser(data)); // ユーザー情報を更新
    }
  };

  return (
    <div className="w-full h-full">
      {/* ヘッダー部分 */}
      <div className="flex items-center space-x-10 bg-[#008069] text-white pt-16 px-10 pb-5">
        <BsArrowLeft className="cursor-pointer text-2xl font-bold" onClick={handleCloseOpenProfile} />
        <p className="cursor-pointer font-semibold">Profile</p>
      </div>

      {/* プロフィール画像更新エリア */}
      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput">
          <img className="rounded-full w-[15vw] h-[15vw] cursor-pointer" src={auth.reqUser.profile || tempPicture || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp?b=1&s=170667a&w=0&k=20&c=wpJ0QJYXdbLx24H5LK08xSgiQ3zNkCAD2W3F74qlUL0="} alt="" />
        </label>

        <input onChange={(e) => uploadToCloudinary(e.target.files[0])} type="file" id="imgInput" className="hidden" />
      </div>

      {/* 名前編集エリア */}
      <div className="bg-white px-3">
        <p className="py-3">Your name</p>

        {/* 編集モードでないとき */}
        {!flag && (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{auth.reqUser?.name || "Username"}</p>
            <BsPencil onClick={handleFlag} className="cursor-pointer" />
          </div>
        )}

        {/* 編集モードのとき */}
        {flag && (
          <div className="w-full flex justify-between items-center py-2">
            <input onKeyPress={handleUpdateName} onChange={handleChange} type="text" placeholder="Enter your name" className="w-[80%] outline-none border-b-2 border-blue-700 p-2" />
            <BsCheck2 onClick={handleCheckClick} className="cursor-pointer text-2xl" />
          </div>
        )}
      </div>

      {/* 補足説明 */}
      <div className="px-3 my-5">
        <p className="py-10">This is not your username or pin. This name will be visible to others.</p>
      </div>
    </div>
  );
};

export default Profile;
