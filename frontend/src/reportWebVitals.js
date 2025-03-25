// Web Vitals（ウェブパフォーマンス測定）のレポートを行う関数
const reportWebVitals = (onPerfEntry) => {
  // `onPerfEntry` が存在し、かつ関数である場合に処理を実行
  if (onPerfEntry && onPerfEntry instanceof Function) {
    // `web-vitals` モジュールを動的にインポート
    import("web-vitals").then(({ getCLS, getFID, getFCP, getLCP, getTTFB }) => {
      // 各種パフォーマンス指標を計測し、`onPerfEntry` コールバック関数に渡す
      getCLS(onPerfEntry); // Cumulative Layout Shift（累積レイアウトシフト）
      getFID(onPerfEntry); // First Input Delay（最初の入力遅延）
      getFCP(onPerfEntry); // First Contentful Paint（最初に描画されるコンテンツ）
      getLCP(onPerfEntry); // Largest Contentful Paint（最大のコンテンツ描画）
      getTTFB(onPerfEntry); // Time to First Byte（最初のバイトまでの時間）
    });
  }
};

// `reportWebVitals` をデフォルトエクスポート
export default reportWebVitals;
