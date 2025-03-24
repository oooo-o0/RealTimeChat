// 必要なアクションタイプ（CREATE_NEW_MESSAGE, GET_ALL_MESSAGE）を外部のActionTypeファイルからインポート
import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGE } from "./ActionType";

// メッセージ管理用の初期状態を定義
const initialValue = {
  messages: null, // メッセージ一覧（初期状態はnull）
  newMessage: null, // 新しく作成されたメッセージを保持（初期状態はnull）
};

// メッセージに関連するアクションを処理するreducer関数
export const messageReducer = (store = initialValue, { type, payload }) => {
  // もしアクションのタイプが CREATE_NEW_MESSAGE の場合、新しいメッセージを追加
  if (type === CREATE_NEW_MESSAGE) {
    return { ...store, newMessage: payload }; // 既存のstoreを維持しつつ newMessage を更新
  }
  // もしアクションのタイプが GET_ALL_MESSAGE の場合、メッセージ一覧を取得
  else if (type === GET_ALL_MESSAGE) {
    return { ...store, messages: payload }; // 既存のstoreを維持しつつ messages を更新
  }
  // 上記以外のアクションタイプの場合、現在のstoreをそのまま返す
  return store;
};
