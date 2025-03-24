// APIのベースURLをインポート（環境設定ファイルから取得）
import { BASE_API_URL } from "../../config/api";

// Reduxのアクションタイプをインポート（アクションを一意に識別するための定数）
import { CREATE_CHAT, CREATE_GROUP, GET_USERS_CHAT } from "./ActionType";

// ==============================
// 1対1のチャットを作成するアクションクリエイター
// ==============================
export const createChat = (chatData) => async (dispatch) => {
  try {
    // サーバーにリクエストを送信（1対1のチャット作成用エンドポイント）
    const res = await fetch(`${BASE_API_URL}/api/chats/single`, {
      method: "POST", // POSTメソッドで新規作成
      headers: {
        "Content-Type": "application/json", // JSONデータを送信
        Authorization: `Bearer ${chatData.token}`, // 認証トークンをヘッダーに追加
      },
      body: JSON.stringify(chatData.data), // リクエストボディにチャットデータを格納
    });

    // サーバーからのレスポンスをJSONとして取得
    const data = await res.json();
    console.log("create chat ", data);

    // Reduxストアを更新するためのアクションをdispatch
    dispatch({ type: CREATE_CHAT, payload: data });
  } catch (error) {
    // エラーが発生した場合はコンソールに出力
    console.log("catch error ", error);
  }
};

// ==============================
// グループチャットを作成するアクションクリエイター
// ==============================
export const createGroupChat = (chatData) => async (dispatch) => {
  try {
    // サーバーにリクエストを送信（グループチャット作成用エンドポイント）
    const res = await fetch(`${BASE_API_URL}/api/chats/group`, {
      method: "POST", // POSTメソッドで新規作成
      headers: {
        "Content-Type": "application/json", // JSONデータを送信
        Authorization: `Bearer ${chatData.token}`, // 認証トークンをヘッダーに追加
      },
      body: JSON.stringify(chatData.group), // グループの情報をリクエストボディに格納
    });

    // サーバーからのレスポンスをJSONとして取得
    const data = await res.json();
    console.log("create group chat ", data);

    // Reduxストアを更新するためのアクションをdispatch
    dispatch({ type: CREATE_GROUP, payload: data });
  } catch (error) {
    // エラーが発生した場合はコンソールに出力
    console.log("catch error ", error);
  }
};

// ==============================
// ユーザーが参加しているチャット一覧を取得するアクションクリエイター
// ==============================
export const getUsersChat = (chatData) => async (dispatch) => {
  try {
    // サーバーにリクエストを送信（ユーザーのチャット一覧取得用エンドポイント）
    const res = await fetch(`${BASE_API_URL}/api/chats/user`, {
      method: "GET", // GETメソッドでデータを取得
      headers: {
        "Content-Type": "application/json", // JSONデータをリクエスト
        Authorization: `Bearer ${chatData.token}`, // 認証トークンをヘッダーに追加
      },
    });

    // サーバーからのレスポンスをJSONとして取得
    const data = await res.json();
    console.log("get users chat ", data);

    // Reduxストアを更新するためのアクションをdispatch
    dispatch({ type: GET_USERS_CHAT, payload: data });
  } catch (error) {
    // エラーが発生した場合はコンソールに出力
    console.log("catch error ", error);
  }
};
