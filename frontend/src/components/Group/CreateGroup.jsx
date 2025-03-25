import { useState } from "react";
import { BsArrowLeft, BsArrowRight } from "react-icons/bs";
import SelectedMember from "./SelectedMember";
import ChatCard from "../ChatCard/ChatCard";
import NewGroup from "./NewGroup";
import { useDispatch, useSelector } from "react-redux";
import { searchUser } from "../../Redux/Auth/Action";

const CreateGroup = ({ setIsGroup }) => {
  const [newGroup, setNewGroup] = useState(false); // グループ作成モードの状態を管理（true: 作成モード, false: 非作成モード）

  const [groupMember, setGroupMember] = useState(new Set()); // 選択されたグループメンバーを管理する状態（Setを使用し、重複を防ぐ）

  const [query, setQuery] = useState(""); // ユーザー検索のクエリ（入力された検索ワード）を管理
  const dispatch = useDispatch(); // Reduxのdispatch関数を取得（アクションをストアに送るために使用）
  const { auth } = useSelector((store) => store); // Reduxストアから認証情報（auth）を取得

  const token = localStorage.getItem("token"); // ローカルストレージからユーザーの認証トークンを取得（APIリクエスト時に使用）

  // グループから特定のメンバーを削除する関数
  const handleRemoveMember = (item) => {
    // 現在のグループメンバーのセットをコピー（元の状態を変更しないように）
    const updatedMembers = new Set(groupMember);
    // 指定したメンバーをセットから削除
    updatedMembers.delete(item);
    // 更新されたセットを `groupMember` の状態にセット
    setGroupMember(updatedMembers);
  };

  // ユーザーを検索する関数（検索キーワードを基に検索）
  const handleSearch = (keyword) => {
    // `searchUser` アクションを Redux の `dispatch` で実行
    // 検索キーワードと認証トークンを渡し、ユーザー検索を実行
    dispatch(searchUser({ keyword, token }));
  };

  return (
    <div className="w-full h-full">
      {/* `newGroup` が `false` の場合にのみ、グループ作成の最初の画面を表示 */}
      {!newGroup && (
        <div>
          {/* ヘッダー部分 */}
          <div className="flex items-center space-x-10 bg-[#069b60] text-white pt-16 px-10 pb-5">
            {/* 戻るボタン（`BsArrowLeft` アイコン） */}
            <BsArrowLeft className="cursor-pointer text-2xl font-bold" />
            <p className="text-xl font-semibold">新しくチャットを作成</p>
          </div>

          {/* ユーザー検索エリア */}
          <div className="relative bg-white py-4 px-3">
            {/* 選択したメンバーの表示と削除機能 */}
            <div className="flex space-x-2 flex-wrap space-y-1">
              {/* `groupMember` にメンバーがいる場合のみ表示 */}
              {groupMember.size > 0 && Array.from(groupMember).map((item, index) => <SelectedMember key={index} handleRemoveMember={(item) => handleRemoveMember(item)} member={item} />)}
            </div>

            {/* メンバー追加用の検索入力フォーム */}
            <input
              type="text"
              className="outline-none border-b border-[#8888] p-2 w-[93%]"
              placeholder="ユーザーを検索"
              value={query} // 入力値を`query`の状態にバインド
              onChange={(e) => {
                handleSearch(e.target.value); // ユーザー検索を実行
                setQuery(e.target.value); // `query` の状態を更新
              }}
            />
          </div>

          {/* ユーザー検索結果を表示するエリア */}
          <div className="bg-white overflow-y-scroll h-[50.3vh]">
            {/* `query` に値がある場合のみ、検索結果を表示 */}
            {query &&
              auth.searchUser?.map((item) => (
                <div
                  onClick={() => {
                    groupMember.add(item); // 選択したユーザーを `groupMember` に追加
                    setGroupMember(groupMember); // 状態を更新
                    setQuery(""); // 検索欄をクリア
                  }}
                  key={item?.id}
                >
                  <hr />
                  {/* ユーザーの情報を表示する `ChatCard` コンポーネント */}
                  <ChatCard userImg={item.profile} name={item.name} />
                </div>
              ))}
          </div>

          {/* 参加者追加が完了し、グループ作成に進むボタン */}
          <div className="bottom-10 py-10 bg-slate-200 items-center justify-center flex">
            <div
              onClick={() => {
                setNewGroup(true); // `newGroup` を `true` にして次のステップへ
              }}
              className="bg-green-600 rounded-full p-4 cursor-pointer"
            >
              {/* 進むボタン（`BsArrowRight` アイコン） */}
              <BsArrowRight className="text-white font-bold text-3xl" />
            </div>
          </div>
        </div>
      )}

      {/* `newGroup` が `true` になったら `NewGroup` コンポーネントをレンダリング */}
      {newGroup && <NewGroup groupMember={groupMember} setIsGroup={setIsGroup} />}
    </div>
  );
};

export default CreateGroup;
