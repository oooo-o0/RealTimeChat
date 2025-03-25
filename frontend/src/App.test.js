import { render, screen } from "@testing-library/react"; // Reactのテストライブラリから必要な関数をインポート
import App from "./App"; // テスト対象のコンポーネント（App）をインポート

// テストケースの定義
test("renders learn react link", () => {
  // Appコンポーネントをレンダリング
  render(<App />);

  // "learn react" というテキストを持つ要素を取得（大文字小文字を無視するオプション付き）
  const linkElement = screen.getByText(/learn react/i);

  // 取得した要素がドキュメント内に存在することを確認
  expect(linkElement).toBeInTheDocument();
});
