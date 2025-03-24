import { useEffect, useState } from "react";
import "./ProgressBar.css";

const ProgressBar = ({ index, activeIndex, duration }) => {
  // このプログレスバーが現在アクティブかどうかを判定
  const isActive = index === activeIndex;

  // プログレスバーの進行状況を管理するステート
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    // プログレスバーの進行を管理するタイマー
    const intervalId = setInterval(() => {
      setProgress((prev) => {
        if (prev < 100) {
          return prev + 1; // 1%ずつ増加
        }
        clearInterval(intervalId); // 100%になったらタイマーを停止
        return prev;
      });
    }, duration / 100); // duration に基づいて 100 分割

    // コンポーネントのアンマウント時または activeIndex が変更されたときにクリーンアップ
    return () => clearInterval(intervalId);
  }, [duration, activeIndex]);

  useEffect(() => {
    // activeIndex が変わるたびにプログレスをリセット
    setProgress(0);
  }, [activeIndex]);

  return (
    <div className={`progress-bar-container ${isActive ? "active" : ""}`}>
      <div className={`${isActive ? "progress-bar" : ""}`} style={{ width: `${progress}%` }}></div>
    </div>
  );
};

export default ProgressBar;
