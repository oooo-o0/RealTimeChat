import React from "react"; // React ライブラリをインポート
import ReactDOM from "react-dom/client"; // React 18 では `react-dom/client` を使用して新しい API を活用
import "./index.css"; // グローバルなスタイルを適用する CSS ファイルをインポート
import App from "./App"; // アプリのルートコンポーネント `App` をインポート
import { BrowserRouter } from "react-router-dom"; // クライアントサイドのルーティングを管理するための `BrowserRouter` をインポート
import { Provider } from "react-redux"; // Redux の `Provider` をインポート（アプリ全体で Redux ストアを使えるようにする）
import { store } from "./Redux/store"; // Redux のストア（`store`）をインポート

// React 18 の新しい API を使用して `root` を作成
const root = ReactDOM.createRoot(document.getElementById("root"));

// アプリのレンダリングを実行
root.render(
  <React.StrictMode>
    {/* ルーティングのために `BrowserRouter` でラップ */}
    <BrowserRouter>
      {/* Redux のストアをアプリ全体で利用できるように `Provider` でラップ */}
      <Provider store={store}>
        <App /> {/* ルートコンポーネント */}
      </Provider>
    </BrowserRouter>
  </React.StrictMode>
);
