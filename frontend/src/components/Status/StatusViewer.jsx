import { useEffect, useState } from "react";
import { stories } from "./DummyStorage";
import ProgressBar from "./ProgressBar";
import { BsArrowLeft } from "react-icons/bs";
import { AiOutlineClose } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
const StatusViewer = () => {
  // 現在表示しているストーリーのインデックスを管理
  const [currentStoryIndex, setCurrentStoryIndex] = useState(0);
  // プログレスバーのアクティブなインデックスを管理
  const [activeIndex, setActiveIndex] = useState(0);
  const navigate = useNavigate();

  // 前のページに戻る処理
  const handleNavigate = () => {
    navigate(-1);
  };

  // 次のストーリーに進む処理
  const handleNextStory = () => {
    if (currentStoryIndex < stories?.length - 1) {
      // 次のストーリーへ移動
      setCurrentStoryIndex(currentStoryIndex + 1);
      setActiveIndex(activeIndex + 1);
    } else {
      // 最後のストーリーの場合、最初に戻る
      setCurrentStoryIndex(0);
      setActiveIndex(0);
    }
  };

  // 一定時間ごとにストーリーを自動的に進める処理
  useEffect(() => {
    const intervalId = setInterval(() => {
      handleNextStory();
    }, 3000); // 3秒ごとに次のストーリーへ

    // コンポーネントのアンマウント時やストーリー変更時にクリーンアップ
    return () => clearInterval(intervalId);
  }, [currentStoryIndex]);

  return (
    <div className="flex justify-center items-center h-screen w-screen bg-slate-900">
      <div className="relative">
        {/* 現在のストーリーを表示 */}
        <img src={stories?.[currentStoryIndex].image} alt="story" className="max-h-[96vh] object-contain" />
        <div className="absolute top-0 flex w-full">
          {/* ストーリーごとのプログレスバー */}
          {stories?.map((item, index) => (
            <ProgressBar key={index} duration={3000} index={index} activeIndex={activeIndex} />
          ))}
        </div>
      </div>
      <div>
        {/* 戻るボタン */}
        <BsArrowLeft onClick={handleNavigate} className="text-white text-4xl cursor-pointer absolute top-3 left-10" />
        {/* 閉じるボタン */}
        <AiOutlineClose onClick={handleNavigate} className="text-white text-4xl cursor-pointer absolute top-3 right-10" />
      </div>
    </div>
  );
};

export default StatusViewer;
