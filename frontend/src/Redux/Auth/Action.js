import { BASE_API_URL } from "../../config/api";
import { LOGIN, LOGOUT, REGISTER, REQ_USER, SEARCH_USER, UPDATE_USER } from "./ActionType";

// ==============================
// ユーザー登録（Sign Up）用のアクションクリエーター
// ==============================
export const register = (data) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/auth/signup`, {
      method: "POST", // HTTP POSTメソッドを使用（新規ユーザー登録）
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), // ユーザー情報をJSONとして送信
    });
    const resData = await res.json();

    // JWTトークンが返された場合は、ローカルストレージに保存
    if (resData.jwt) localStorage.setItem("token", resData.jwt);

    console.log("register", resData);
    dispatch({ type: REGISTER, payload: resData }); // ユーザー登録情報を Redux ストアに保存
  } catch (error) {
    console.log("catch error", error);
  }
};

// ==============================
// ユーザーログイン（Sign In）用のアクションクリエーター
// ==============================
export const login = (data) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/auth/signin`, {
      method: "POST", // HTTP POSTメソッドを使用（ログイン）
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), // ログイン情報をJSONとして送信
    });
    const resData = await res.json();

    // JWTトークンが返された場合は、ローカルストレージに保存
    if (resData.jwt) localStorage.setItem("token", resData.jwt);

    console.log("login", resData);
    dispatch({ type: LOGIN, payload: resData }); // ログイン情報を Redux ストアに保存
  } catch (error) {
    console.log("catch error", error);
  }
};

// ==============================
// 現在のユーザー情報を取得するアクションクリエーター
// ==============================
export const currentUser = (token) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/users/profile`, {
      method: "GET", // HTTP GETメソッドを使用（ユーザープロフィール取得）
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`, // 認証用のトークンをヘッダーに追加
      },
    });
    const resData = await res.json();

    console.log("current user", resData);
    dispatch({ type: REQ_USER, payload: resData }); // 取得したユーザー情報を Redux ストアに保存
  } catch (error) {
    console.log("catch error", error);
  }
};

// ==============================
// ユーザー検索を行うアクションクリエーター
// ==============================
export const searchUser = (data) => async (dispatch) => {
  try {
    console.log(data);
    const res = await fetch(`${BASE_API_URL}/api/users/${data.keyword}`, {
      method: "GET", // HTTP GETメソッドを使用（ユーザー検索）
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${data.token}`, // 認証用のトークンをヘッダーに追加
      },
    });
    const resData = await res.json();

    console.log("search", resData);
    dispatch({ type: SEARCH_USER, payload: resData }); // 検索結果を Redux ストアに保存
  } catch (error) {
    console.log("catch error", error);
  }
};

// ==============================
// ユーザー情報を更新するアクションクリエーター
// ==============================
export const updateUser = (data) => async (dispatch) => {
  try {
    const res = await fetch(`${BASE_API_URL}/api/users/update`, {
      method: "PUT", // HTTP PUTメソッドを使用（ユーザー情報更新）
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${data.token}`, // 認証用のトークンをヘッダーに追加
      },
      body: JSON.stringify(data.data), // 更新するデータをJSONとして送信
    });
    const resData = await res.json();

    console.log("updated user", resData);
    dispatch({ type: UPDATE_USER, payload: resData }); // 更新されたユーザー情報を Redux ストアに保存
  } catch (error) {
    console.log("catch error", error);
  }
};

// ==============================
// ユーザーログアウト用のアクションクリエーター
// ==============================
export const logoutAction = () => async (dispatch) => {
  // ローカルストレージからJWTトークンを削除
  localStorage.removeItem("token");

  // Reduxストアのユーザー情報をクリア
  dispatch({ type: LOGOUT, payload: null }); // ログアウト処理
  dispatch({ type: REQ_USER, payload: null }); // 現在のユーザー情報をクリア
};
