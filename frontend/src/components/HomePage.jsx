import React, { useEffect, useRef, useState } from "react";
import "./HomePage.css";
import { useNavigate } from "react-router-dom";
import Profile from "./Profile/Profile";
import CreateGroup from "./Group/CreateGroup";
import { useDispatch, useSelector } from "react-redux";
import { currentUser, logoutAction, searchUser } from "../Redux/Auth/Action";
import { createChat, getUsersChat } from "../Redux/Chat/Action";
import { createMessage, getAllMessages } from "../Redux/Message/Action";

import ProfileSection from "./HomeComponents/ProfileSection";
import SearchBar from "./HomeComponents/SearchBar";
import ChatList from "./HomeComponents/ChatList";
import MessageCard from "./MessageCard/MessageCard";
import { AiOutlineSearch } from "react-icons/ai";
import { BsEmojiSmile, BsMicFill, BsThreeDotsVertical } from "react-icons/bs";
import { ImAttachment } from "react-icons/im";
import { Client } from "@stomp/stompjs"; // Clientを正しくインポート
import SockJS from "sockjs-client"; // SockJSもインポート

function HomePage() {
  const [querys, setQuerys] = useState(""); // 検索クエリの状態を管理
  const [currentChat, setCurrentChat] = useState(null); // 現在のチャットルーム情報を保持
  const [content, setContent] = useState(""); // 入力されたメッセージの内容を保持
  const [isProfile, setIsProfile] = useState(false); // プロフィール表示の状態を管理
  const navigate = useNavigate(); // 画面遷移のためのフック
  const [isGroup, setIsGroup] = useState(false); // グループチャットかどうかを判定
  const [anchorEl, setAnchorEl] = useState(null); // メニューのアンカー要素（開閉を管理）
  const open = Boolean(anchorEl); // メニューが開いているかどうかの状態
  const dispatch = useDispatch(); // Reduxのdispatch関数（アクションを発行）
  const { auth, chat, message } = useSelector((store) => store); // Reduxストアから認証情報・チャット情報・メッセージ情報を取得
  const token = localStorage.getItem("token"); // ローカルストレージからJWTトークンを取得
  const [stompClient, setStompClient] = useState(null); // WebSocketのSTOMPクライアントを管理
  const [isConnected, setIsConnected] = useState(false); // WebSocketの接続状態を管理
  const [messages, setMessages] = useState([]); // 受信したメッセージのリストを保持
  const [lastMessages, setLastMessages] = useState({}); // 各チャットの最新メッセージを管理
  const messageContainerRef = useRef(null); // メッセージリストのスクロール制御のためのref
  useEffect(() => {
    // メッセージが更新されるたびにスクロールを最下部に移動
    if (messageContainerRef.current) {
      messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
    }
  }, [messages]);

  // WebSocket接続を確立する関数
  const connect = () => {
    const sock = new SockJS("http://localhost:8080/ws"); // SockJSインスタンスを作成

    const stompClient = new Client({
      webSocketFactory: () => sock, // SockJSをWebSocket接続に使う
      onConnect: onConnect, // 接続成功時のコールバック
      onStompError: onError, // エラー時のコールバック
    });

    setStompClient(stompClient); // stompClientをステートに設定
    stompClient.activate(); // WebSocket接続を開始
  };

  // 指定した名前のクッキーを取得する関数
  function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
      return parts.pop().split(";").shift();
    }
  }

  // WebSocket接続エラー時のコールバック
  const onError = (error) => {
    console.log("on error ", error);
  };

  // WebSocket接続成功時のコールバック
  const onConnect = () => {
    setIsConnected(true);

    // 現在のチャットに応じてWebSocketの購読を設定
    if (stompClient && currentChat) {
      if (currentChat.isGroupChat) {
        // グループチャットのメッセージを購読
        stompClient.subscribe(`/group/${currentChat?.id}`, onMessageReceive);
      } else {
        // ユーザー間のダイレクトメッセージを購読
        stompClient.subscribe(`/user/${currentChat?.id}`, onMessageReceive);
      }
    }
  };

  // WebSocketから受信したメッセージを処理するコールバック
  const onMessageReceive = (payload) => {
    const receivedMessage = JSON.parse(payload.body);
    setMessages((prevMessages) => [...prevMessages, receivedMessage]);
  };

  // WebSocket接続を確立するエフェクト
  useEffect(() => {
    connect();
  }, []);

  // WebSocket接続が確立されたらチャットを購読するエフェクト
  useEffect(() => {
    if (isConnected && stompClient && currentChat?.id) {
      const subscription = currentChat.isGroupChat ? stompClient.subscribe(`/group/${currentChat.id}`, onMessageReceive) : stompClient.subscribe(`/user/${currentChat.id}`, onMessageReceive);

      return () => {
        subscription.unsubscribe();
      };
    }
  }, [isConnected, stompClient, currentChat]);

  // 新しいメッセージをWebSocket経由で送信するエフェクト
  useEffect(() => {
    if (message.newMessage && stompClient) {
      stompClient.send("/app/message", {}, JSON.stringify(message.newMessage));
      setMessages((prevMessages) => [...prevMessages, message.newMessage]);
    }
  }, [message.newMessage, stompClient]);

  // Reduxストアのメッセージデータを `messages` ステートに反映するエフェクト
  useEffect(() => {
    if (message.messages) {
      setMessages(message.messages);
    }
  }, [message.messages]);

  // チャットが変更されるたびにメッセージを取得するエフェクト
  useEffect(() => {
    if (currentChat?.id) {
      dispatch(getAllMessages({ chatId: currentChat.id, token }));
    }
  }, [currentChat, message.newMessage]);

  // ユーザーのチャットとグループを取得するエフェクト
  useEffect(() => {
    dispatch(getUsersChat({ token }));
  }, [chat.createdChat, chat.createdGroup]);

  // ユーザーメニューを開く処理
  const handleClick = (e) => {
    setAnchorEl(e.currentTarget);
  };

  // ユーザーメニューを閉じる処理
  const handleClose = () => {
    setAnchorEl(null);
  };

  // チャットカードがクリックされたときの処理
  const handleClickOnChatCard = (userId) => {
    dispatch(createChat({ token, data: { userId } }));
  };

  // ユーザー検索の処理
  const handleSearch = (keyword) => {
    dispatch(searchUser({ keyword, token }));
  };

  // 新しいメッセージを作成する処理
  const handleCreateNewMessage = () => {
    dispatch(
      createMessage({
        token,
        data: { chatId: currentChat.id, content: content },
      })
    );
    setContent(""); // メッセージ送信後に入力欄をクリア
  };
  // ユーザー情報を取得するエフェクト
  useEffect(() => {
    dispatch(currentUser(token));
  }, [token]);

  // 現在のチャットを設定する関数
  const handleCurrentChat = (item) => {
    setCurrentChat(item);
  };

  // チャットが変更された際にメッセージを取得するエフェクト
  useEffect(() => {
    chat?.chats &&
      chat?.chats?.forEach((item) => {
        dispatch(getAllMessages({ chatId: item.id, token }));
      });
  }, [chat?.chats, token, dispatch]);

  // メッセージの変更時に lastMessages を更新するエフェクト
  useEffect(() => {
    const prevLastMessages = { ...lastMessages };
    if (message.messages && message.messages.length > 0) {
      message.messages.forEach((msg) => {
        prevLastMessages[msg.chat.id] = msg;
      });

      setLastMessages(prevLastMessages);
    }
  }, [message.messages]);

  // ユーザーのプロフィール画面に遷移する関数
  const handleNavigate = () => {
    setIsProfile(true);
  };

  // プロフィール画面を閉じる関数
  const handleCloseOpenProfile = () => {
    setIsProfile(false);
  };

  // 新しいグループを作成する関数
  const handleCreateGroup = () => {
    setIsGroup(true);
  };

  // ユーザーをログアウトする関数
  const handleLogout = () => {
    dispatch(logoutAction());
    navigate("/signin");
  };

  // ユーザーの認証状態を確認するエフェクト
  useEffect(() => {
    if (!auth.reqUser) {
      navigate("/signin");
    }
  }, [auth.reqUser]);

  return (
    <div className="relative">
      {/* ヘッダー部分 */}
      <div className="w-[100vw] py-14 bg-[#b0e5ff]">
        <div className="flex bg-[#f0f2f5] h-[90vh] absolute top-[5vh] left-[2vw] w-[96vw] floating-box">
          {/* 左サイドバー */}
          <div className="left w-[30%] h-full bg-[#e8e9ec]">
            {isProfile && (
              <div className="w-full h-full">
                <Profile handleCloseOpenProfile={handleCloseOpenProfile} />
              </div>
            )}
            {isGroup && <CreateGroup setIsGroup={setIsGroup} />}
            {!isProfile && !isGroup && (
              <div className="w-full">
                {/* プロフィールセクション */}
                <ProfileSection auth={auth} isProfile={isProfile} isGroup={isGroup} handleNavigate={handleNavigate} handleClick={handleClick} handleCreateGroup={handleCreateGroup} handleLogout={handleLogout} handleClose={handleClose} open={open} anchorEl={anchorEl} />
                {/* 検索バー */}
                <SearchBar querys={querys} setQuerys={setQuerys} handleSearch={handleSearch} />
                {/* チャットリスト */}
                <ChatList querys={querys} auth={auth} chat={chat} lastMessages={lastMessages} handleClickOnChatCard={handleClickOnChatCard} handleCurrentChat={handleCurrentChat} />
              </div>
            )}
          </div>

          {/* 初期表示 */}
          {!currentChat?.id && (
            <div className="w-[70%] flex flex-col items-center justify-center h-full">
              <div className="max-w-[70%] text-center">
                <img className="ml-11 lg:w-[75%]" src="https://cdn.pixabay.com/photo/2024/06/28/04/49/bubble-8858495_640.png" alt="whatsapp-icon" />
                <h1 className="text-4xl text-gray-600">RealTimeChat</h1>
                <p className="my-9">リアルタイムでのメッセージ送受信ができます.</p>
              </div>
            </div>
          )}

          {/* メッセージセクション */}
          {currentChat?.id && (
            <div className="w-[70%] relative bg-blue-200">
              {/* メッセージのヘッダー部分 */}
              <div className="header absolute top-0 w-full bg-[#f0f2f5]">
                <div className="flex justify-between">
                  <div className="py-3 space-x-4 flex items-center px-3">
                    <img className="w-10 h-10 rounded-full" src={currentChat.group ? currentChat.chat_image || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp" : auth.reqUser?.id !== currentChat.users[0]?.id ? currentChat.users[0]?.profile || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp" : currentChat.users[1]?.profile || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp"} alt="profile" />
                    <p>{currentChat.group ? currentChat.chatName : auth.reqUser?.id !== currentChat.users[0]?.id ? currentChat.users[0].name : currentChat.users[1].name}</p>
                  </div>
                  <div className="flex py-3 space-x-4 items-center px-3">
                    <AiOutlineSearch />
                    <BsThreeDotsVertical />
                  </div>
                </div>
              </div>

              {/* メッセージ表示部分 */}
              <div className="px-10 h-[85vh] overflow-y-scroll pb-10" ref={messageContainerRef}>
                <div className="space-y-1 w-full flex flex-col justify-center items-end mt-20 py-2">{messages?.length > 0 && messages?.map((item, i) => <MessageCard key={i} isReqUserMessage={item?.user?.id !== auth?.reqUser?.id} content={item.content} timestamp={item.timestamp} profilePic={item?.user?.profile || "https://media.istockphoto.com/id/521977679/photo/silhouette-of-adult-woman.webp"} />)}</div>
              </div>

              {/* メッセージ入力セクション */}
              <div className="footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl">
                <div className="flex justify-between items-center px-5 relative">
                  <BsEmojiSmile className="cursor-pointer" />
                  <ImAttachment />

                  {/* メッセージ入力フィールド */}
                  <input
                    className="py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]"
                    type="text"
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="Type message"
                    value={content}
                    onKeyPress={(e) => {
                      if (e.key === "Enter") {
                        handleCreateNewMessage();
                        setContent("");
                      }
                    }}
                  />
                  <BsMicFill />
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default HomePage;
