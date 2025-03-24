// チャット関連のアクションタイプを外部のActionTypeファイルからインポート
import { CREATE_CHAT, CREATE_GROUP, GET_USERS_CHAT } from "./ActionType";

// チャット管理用の初期状態を定義
const initialValue = {
  chats: [], // ユーザーが参加しているチャット一覧（初期状態は空の配列）
  createdGroup: null, // 新しく作成されたグループチャットの情報を保持（初期状態はnull）
  createdChat: null, // 新しく作成された1対1のチャットの情報を保持（初期状態はnull）
};

// チャットに関連するアクションを処理するreducer関数
export const chatReducer = (store = initialValue, { type, payload }) => {
  // もしアクションのタイプが CREATE_CHAT の場合、新しい1対1のチャットを作成
  if (type === CREATE_CHAT) {
    return { ...store, createdChat: payload }; // 既存のstoreを維持しつつ createdChat を更新
  }
  // もしアクションのタイプが CREATE_GROUP の場合、新しいグループチャットを作成
  else if (type === CREATE_GROUP) {
    return { ...store, createdGroup: payload }; // 既存のstoreを維持しつつ createdGroup を更新
  }
  // もしアクションのタイプが GET_USERS_CHAT の場合、ユーザーのチャット一覧を取得
  else if (type === GET_USERS_CHAT) {
    return { ...store, chats: payload }; // 既存のstoreを維持しつつ chats を更新
  }
  // 上記以外のアクションタイプの場合、現在のstoreをそのまま返す
  return store;
};
