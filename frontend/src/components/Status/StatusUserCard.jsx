import { useNavigate } from "react-router-dom";

const StatusUserCard = () => {
  const navigate = useNavigate();

  // ユーザーのステータスページへ遷移する関数
  const handleNavigate = () => {
    navigate(`/status/userId`); // 'userId' は実際のユーザー ID に置き換える必要がある
  };

  return (
    <div onClick={handleNavigate} className="flex items-center p-3 cursor-pointer">
      <div>
        {/* ユーザーのアバター画像 */}
        <img className="h-7 w-7 lg:w-10 lg:h-10 rounded-full" src="https://cdn.pixabay.com/photo/2023/09/11/14/19/weight-8246973_640.jpg" alt="User Avatar" />
      </div>
      <div className="ml-2 text-white">
        {/* ユーザー名やステータス情報を表示（実際の情報に置き換える必要あり） */}
        <p>Something Something</p>
      </div>
    </div>
  );
};

export default StatusUserCard;
