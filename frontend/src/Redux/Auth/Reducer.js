// Reduxで使用するアクションタイプをインポート
import { LOGIN, REGISTER, REQ_USER, SEARCH_USER, UPDATE_USER } from "./ActionType";

// ==============================
// 認証（Authentication）に関する初期状態（Reduxストアの初期データ）
// ==============================
const initialValue = {
  signup: null, // ユーザーの登録情報を保持
  signin: null, // ユーザーのログイン情報を保持
  reqUser: null, // 現在ログインしているユーザーの情報を保持
  searchUser: null, // 検索したユーザーの情報を保持
  updateUser: null, // ユーザー情報の更新データを保持
};

// ==============================
// 認証関連のアクションを処理するReducer
// ==============================
export const authReducer = (store = initialValue, { type, payload }) => {
  // 受け取ったアクションの種類（type）によってstoreを更新する
  if (type === REGISTER) {
    return { ...store, signup: payload }; // ユーザー登録時のデータを更新
  } else if (type === LOGIN) {
    return { ...store, signin: payload }; // ユーザーログイン時のデータを更新
  } else if (type === REQ_USER) {
    return { ...store, reqUser: payload }; // 現在ログインしているユーザーの情報を更新
  } else if (type === SEARCH_USER) {
    return { ...store, searchUser: payload }; // ユーザー検索結果を更新
  } else if (type === UPDATE_USER) {
    return { ...store, updateUser: payload }; // ユーザー情報の更新結果を更新
  }

  // 未知のアクションタイプが来た場合は現在のstoreの状態を維持する
  return store;
};
