import { Alert, Button, Snackbar } from "@mui/material";
import { green } from "@mui/material/colors";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { currentUser, login } from "../../Redux/Auth/Action";

const Signin = () => {
  // スナックバー（通知バー）の開閉状態を管理するステート
  const [openSnackbar, setOpenSnackbar] = useState(false);

  // 入力データ（email と password）を管理するステート
  const [inputData, setInputData] = useState({ email: "", password: "" });

  // Redux の dispatch 関数を取得（アクションを発行するために使用）
  const dispatch = useDispatch();

  // 画面遷移用の関数を取得
  const navigate = useNavigate();

  // ローカルストレージからトークンを取得
  const token = localStorage.getItem("token");

  // Redux ストアから認証情報を取得
  const { auth } = useSelector((store) => store);

  // フォームの送信処理
  const handleSubmit = (e) => {
    e.preventDefault(); // フォームのデフォルトの送信動作を防ぐ

    // Redux の login アクションを dispatch してログイン処理を実行
    dispatch(login(inputData));

    // スナックバーを表示
    setOpenSnackbar(true);
  };

  // 入力値が変更されたときに呼ばれる関数
  const handleChange = (e) => {
    const { name, value } = e.target;

    // 入力データのステートを更新
    setInputData((values) => ({ ...values, [name]: value }));
  };

  // スナックバーを閉じる処理
  const handleSnackbarClose = () => {
    setOpenSnackbar(false);
  };

  // コンポーネントのマウント時、または `token` が変更されたときに実行
  useEffect(() => {
    if (token) {
      // トークンが存在する場合、現在のユーザー情報を取得するアクションを dispatch
      dispatch(currentUser(token));
    }
  }, [dispatch, token]);

  // 認証情報が取得できたらホーム画面に遷移
  useEffect(() => {
    if (auth.reqUser?.name) {
      navigate("/");
    }
  }, [auth.reqUser, navigate]);

  return (
    <div>
      {/* 画面中央に配置されたサインインフォーム */}
      <div className="flex justify-center h-screen w-[100vw] items-center">
        <div className="p-10 w-[30%] shadow-md bg-white">
          <form onSubmit={handleSubmit} className="space-y-5 ">
            <div>
              <p className="mb-2">Email</p>
              {/* Email 入力欄 */}
              <input type="text" name="email" placeholder="メールアドレスを入力" onChange={handleChange} value={inputData.email} className="py-2 outline outline-green-600 w-full rounded-md border" />
            </div>
            <div>
              <p className="mb-2">パスワード</p>
              {/* パスワード入力欄（type を password に設定し、セキュリティを向上） */}
              <input type="password" name="password" placeholder="パスワードを入力" onChange={handleChange} value={inputData.password} className="py-2 outline outline-green-600 w-full rounded-md border" />
            </div>
            <div>
              {/* サインインボタン */}
              <Button type="submit" sx={{ bgcolor: green[700], padding: ".5rem 0rem" }} className="w-full" variant="contained">
                ログイン
              </Button>
            </div>
          </form>

          {/* 新規アカウント作成リンク */}
          <div className="flex space-x-3 items-center mt-5">
            <p className="m-0">初めてご利用される方はこちら</p>
            <Button variant="text" onClick={() => navigate("/signup")}>
              新規アカウント作成
            </Button>
          </div>
        </div>
      </div>

      {/* ログイン成功時のスナックバー */}
      <div>
        <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleSnackbarClose}>
          <Alert onClose={handleSnackbarClose} severity="success" sx={{ width: "100%" }}>
            Login Successfully!!
          </Alert>
        </Snackbar>
      </div>
    </div>
  );
};

export default Signin;
