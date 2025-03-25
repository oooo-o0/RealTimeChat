// jest-dom は、Jest のカスタムマッチャーを提供するライブラリ
// これにより、DOM ノードに対するアサーション（検証）が簡単になる

// 例えば、以下のようなアサーションが可能になる
// expect(element).toHaveTextContent(/react/i)
// → 要素が "react" というテキストを含んでいるかを検証

// 詳細は公式ドキュメントを参照
// https://github.com/testing-library/jest-dom

// `@testing-library/jest-dom` をインポートすることで
// Jest のテスト環境に追加のマッチャーを利用できるようにする
import "@testing-library/jest-dom";
