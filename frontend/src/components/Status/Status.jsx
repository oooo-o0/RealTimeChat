import { AiOutlineClose } from "react-icons/ai";
import StatusUserCard from "./StatusUserCard";
import { useNavigate } from "react-router-dom";

const Status = () => {
  const navigate = useNavigate();

  // 前のページに戻る関数
  const handleNavigate = () => {
    navigate(-1); // 1つ前のページに戻る
  };

  return (
    <div className="relative">
      <div className="flex items-center w-screen">
        {/* 左側の部分 */}
        <div className="left h-screen bg-[#1e262c] lg:w-2/5 w-2/3 px-5">
          <div className="pt-5 h-[13%]">
            {/* ユーザーのステータスカードを表示 */}
            <StatusUserCard />
          </div>
          <hr />
          <div className="overflow-y-scroll h-[86%] pt-3">
            {/* 仮のデータとして5つのステータスカードをマッピング */}
            {[1, 1, 1, 1, 1].map((item, index) => (
              <StatusUserCard key={index} />
            ))}
          </div>
        </div>

        {/* 右側の部分（閉じるボタン付き） */}
        <div onClick={handleNavigate} className="right relative h-screen lg:w-3/5 w-1/3 bg-[#0b141a]">
          {/* 閉じるボタン */}
          <AiOutlineClose className="text-white cursor-pointer absolute top-5 right-10 text-xl" />
        </div>
      </div>
    </div>
  );
};

export default Status;
