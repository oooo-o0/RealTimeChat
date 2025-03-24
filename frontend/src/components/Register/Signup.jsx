import { Alert, Button, Snackbar } from "@mui/material";
import { green } from "@mui/material/colors";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { currentUser, register } from "../../Redux/Auth/Action";

const Signup = () => {
  // スナックバー（通知メッセージ）の開閉状態を管理するステート
  const [openSnackbar, setOpenSnackbar] = useState(false);

  // 入力フォームのデータを管理するステート
  const [inputData, setInputData] = useState({
    email: "",
    password: "",
    name: "",
  });

  // Reduxのstoreから認証情報(auth)を取得
  const { auth } = useSelector((store) => store);

  // ページ遷移のための関数
  const navigate = useNavigate();

  // Reduxのアクションをディスパッチするための関数
  const dispatch = useDispatch();

  // ローカルストレージからトークンを取得
  const token = localStorage.getItem("token");

  // フォームが送信されたときの処理
  const handleSubmit = (e) => {
    e.preventDefault(); // デフォルトのフォーム送信動作を防ぐ
    dispatch(register(inputData)); // Reduxのregisterアクションを実行し、ユーザー登録を行う
    setOpenSnackbar(true); // スナックバーを表示
  };

  // 入力フォームの値が変更されたときの処理
  const handleChange = (e) => {
    const { name, value } = e.target;
    setInputData((values) => ({ ...values, [name]: value })); // 入力値を更新
  };

  // スナックバーを閉じる処理
  const handleSnackbarClose = () => {
    setOpenSnackbar(false);
  };

  // コンポーネントのマウント時とtokenが変更されたときに現在のユーザー情報を取得
  useEffect(() => {
    if (token) dispatch(currentUser(token));
  }, [token]);

  // ユーザーがログイン済みの場合、ホーム画面にリダイレクト
  useEffect(() => {
    if (auth.reqUser?.name) {
      navigate("/");
    }
  }, [auth.reqUser, navigate]);

  return (
    <div>
      {/* サインアップフォームのレイアウト */}
      <div className="flex flex-col justify-center min-h-screen w-[100vw] items-center">
        <div className="p-10 w-[30%] shadow-md bg-white">
          {/* フォームの送信時にhandleSubmitが呼ばれる */}
          <form onSubmit={handleSubmit} className="space-y-5 ">
            <div>
              <p className="mb-2">ユーザー名</p>
              <input type="text" name="name" placeholder="作成したいアカウント名を入力" onChange={handleChange} value={inputData.name} className="py-2 outline outline-green-600 w-full rounded-md border" />
            </div>
            <div>
              <p className="mb-2">Email</p>
              <input type="text" placeholder="お持ちのメールアドレスを入力" onChange={handleChange} value={inputData.email} name="email" className="py-2 outline outline-green-600 w-full rounded-md border" />
            </div>
            <div>
              <p className="mb-2">パスワード</p>
              <input
                type="password" // パスワード入力フィールド（セキュリティのため）
                name="password"
                placeholder="使用したいパスワードを入力"
                onChange={handleChange}
                value={inputData.password}
                className="py-2 outline outline-green-600 w-full rounded-md border"
              />
            </div>
            <div>
              {/* サインアップボタン（送信時にhandleSubmitが発火） */}
              <Button type="submit" sx={{ bgcolor: green[700], padding: ".5rem 0rem" }} className="w-full" variant="contained">
                アカウントを作成
              </Button>
            </div>
          </form>

          {/* 既にアカウントを持っている場合のログイン案内 */}
          <div className="flex space-x-3 items-center mt-5">
            <p className="m-0">すでにアカウントをお持ちですか?</p>
            <Button variant="text" onClick={() => navigate("/signin")}>
              ログイン
            </Button>
          </div>
        </div>
      </div>

      {/* サインアップ成功時の通知（スナックバー） */}
      <div>
        <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleSnackbarClose}>
          <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: "100%" }}>
            アカウントの作成に成功しました
          </Alert>
        </Snackbar>
      </div>
    </div>
  );
};

export default Signup;
