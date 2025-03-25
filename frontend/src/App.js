import { Route, Routes } from "react-router-dom"; // `react-router-dom` から `Route` と `Routes` をインポートして、アプリのルーティングを管理
import "./App.css"; // アプリケーションのスタイルを定義した CSS ファイルをインポート
import HomePage from "./components/HomePage"; // ホームページのコンポーネントをインポート
import Status from "./components/Status/Status"; // ステータスページのコンポーネントをインポート
import StatusViewer from "./components/Status/StatusViewer"; // 特定のユーザーのステータスを表示するコンポーネントをインポート
import Signin from "./components/Register/Signin"; // サインインページのコンポーネントをインポート
import Signup from "./components/Register/Signup"; // サインアップページのコンポーネントをインポート

function App() {
  return (
    <div>
      {/* ルーティング設定 */}
      <Routes>
        {/* ルートパスでホームページを表示 */}
        <Route path="/" element={<HomePage />} />
        {/* "/status" パスでステータスページを表示 */}
        <Route path="/status" element={<Status />} />
        {/* "/status/:userId" パスで特定のユーザーのステータスを表示 */}
        <Route path="/status/:userId" element={<StatusViewer />} />
        {/* "/signin" パスでサインインページを表示 */}
        <Route path="/signin" element={<Signin />} />
        {/* "/signup" パスでサインアップページを表示 */}
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </div>
  );
}
export default App;
