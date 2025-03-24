// APIのベースURLを設定ファイルからインポート
import { BASE_API_URL } from "../../config/api";
// アクションタイプをインポート
import { CREATE_NEW_MESSAGE, GET_ALL_MESSAGE } from "./ActionType.js";

// 新しいメッセージを作成するためのアクションクリエーター
export const createMessage = (messageData) => async (dispatch) => {
  try {
    // サーバーに新しいメッセージを送信
    const res = await fetch(`${BASE_API_URL}/api/messages/create`, {
      method: "POST", // HTTPメソッドはPOST（データを送信）
      headers: {
        "Content-Type": "application/json", // JSONデータを送信
        Authorization: `Bearer ${messageData.token}`, // 認証トークンをヘッダーに追加
      },
      body: JSON.stringify(messageData.data), // メッセージデータをJSON形式に変換
    });

    // サーバーからのレスポンスをJSONとして解析
    const data = await res.json();
    console.log("create message ", data); // デバッグ用のログ出力

    // 取得したデータをReduxストアに保存するためにディスパッチ
    dispatch({ type: CREATE_NEW_MESSAGE, payload: data });
  } catch (error) {
    console.log("catch error ", error); // エラー発生時のログ出力
  }
};

// 特定のチャットのメッセージ一覧を取得するためのアクションクリエーター
export const getAllMessages = (reqData) => async (dispatch) => {
  console.log("Came inside get all messages"); // デバッグ用のログ出力

  try {
    // 指定されたチャットIDのメッセージを取得
    const res = await fetch(`${BASE_API_URL}/api/messages/${reqData.chatId}`, {
      method: "GET", // HTTPメソッドはGET（データを取得）
      headers: {
        "Content-Type": "application/json", // JSON形式のデータを受け取る
        Authorization: `Bearer ${reqData.token}`, // 認証トークンをヘッダーに追加
      },
    });

    // サーバーからのレスポンスをJSONとして解析
    const data = await res.json();
    console.log("get all messages from action method", data); // デバッグ用のログ出力

    // 取得したデータをReduxストアに保存するためにディスパッチ
    dispatch({ type: GET_ALL_MESSAGE, payload: data });
  } catch (error) {
    console.log("catch error ", error); // エラー発生時のログ出力
  }
};
