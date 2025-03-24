import { combineReducers, legacy_createStore, applyMiddleware } from "redux";
import { thunk } from "redux-thunk";
import { authReducer } from "./Auth/Reducer";
import { chatReducer } from "./Chat/Reducer";
import { messageReducer } from "./Message/Reducer";

// 複数のリデューサー（状態管理用の関数）を1つにまとめる
const rootReducer = combineReducers({
  auth: authReducer, // 認証（ログイン・ログアウト）に関する状態を管理するリデューサー
  chat: chatReducer, // チャットに関する状態を管理するリデューサー
  message: messageReducer, // メッセージに関する状態を管理するリデューサー
});

// Reduxストアを作成し、rootReducerを適用
// applyMiddleware(thunk) を使用することで、非同期処理（API通信など）を扱いやすくする
export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));
