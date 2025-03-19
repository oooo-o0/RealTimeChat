import React from "react";
import { useState } from "react";
import { TbCircleDashed } from "react-icons/tb";
import { BiCommentDetail } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import { BsEmojiSmile, BsFilter, BsMicFill, BsThreeDotsVertical } from "react-icons/bs";
import { ImAttachment } from "react-icons/im";
import ChatCard from "./ChatCard/ChatCard";
import MessageCard from "./MessageCard/MessageCard";
import "./HomePage.css";

const HomePage = () => {
  const [querys, setQuerys] = useState(null);
  const [currentChat, setCurentChat] = useState(null);
  const [content, setContent] = useState("");

  const handleClickOneChatCard = () => [setCurentChat(true)];

  const handleSearch = () => {};

  const handleCreateNewMessage = () => {};

  return (
    <div className="relative">
      <div className="w-[100vw] py-14 bg-[#aaeeff]">
        <div className="flex bg-[#f0f2f5] h-[90vh] absolute top-[5vh] left-[2vw] w-[96vw]">
          <div className="left w-[30%] bg-[#f1fcfc] h-full">
            <div class="W-full">
              <div className="flex justify-between items-center p-3">
                <div className="flex items-center space-x-3">
                  <img className="rounded-full w-10 h-10 cursor-pointer" src="https://cdn.pixabay.com/photo/2020/06/13/17/51/milky-way-5295160_1280.jpg" alt="" />
                  <p>username</p>
                </div>
                <div className="space-x-3 text-2xl flex">
                  <TbCircleDashed />
                  <BiCommentDetail />
                </div>
              </div>

              <div className="relative flex justify-center items-center bg-white py-4 px-3">
                <input
                  className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-2"
                  type="text"
                  placeholder="検索 or 新しくチャットを作成"
                  onChange={(e) => {
                    setQuerys(e.target.value);
                    handleSearch(e.target.value);
                  }}
                  value={querys}
                />
                <AiOutlineSearch className="absolute left-5 top-7" />
                <div>
                  <BsFilter className="ml-4 text-3xl" />
                </div>
              </div>
              {/* 全てのチャット */}
              <div className="bg-white overflow-y-scroll h-[76vh] px-3">
                {querys &&
                  [1, 1, 1, 1, 1].map((item) => (
                    <div onClick={handleClickOneChatCard}>
                      <hr />
                      <ChatCard />
                    </div>
                  ))}
              </div>
            </div>
          </div>

          {!currentChat && (
            <div className="w-[70%] flex flex-col items-center justify-center border h-full">
              <div className="max-w-[70%] text-center">
                <img src="https://cdn.pixabay.com/photo/2024/06/28/04/49/bubble-8858495_640.png" alt="" />
                <h1 className="text-4xl text-gray-600">RealTimeChat Web</h1>
                <p className="my-9">リアルタイム通信で会話ができます。</p>
              </div>
            </div>
          )}

          {currentChat && (
            <div className="w-[70%] relative bg-blue-200">
              <div className="header absolute top-0 w-full bg-[#f0f8fc]">
                <div className="flex justify-between">
                  <div className="py-3 space-x-4 flex items-center px-3">
                    <img className="w-10 h-10 rounded-full" src="https://cdn.pixabay.com/photo/2024/02/15/16/57/cat-8575768_640.png" alt="" />
                    <p>username</p>
                  </div>
                  <div className="py-3 flex space-x-4 items-center px-3">
                    <AiOutlineSearch />
                    <BsThreeDotsVertical />
                  </div>
                </div>
              </div>

              <div className="px-10 h-[85vh] overflow-y-scroll">
                <div className="space-y-1 flex flex-col justify-center mt-20 py-2">
                  {[1, 1, 1, 1, 1].map((item, i) => (
                    <MessageCard isReqUserMessage={i % 2 === 0} content={"message"} />
                  ))}
                </div>
              </div>

              <div className="footer bg-[#f0f8fc] absolute bottom-0 w-full py-3 text-2xl">
                <div className="flex justify-between items-center px-5">
                  <BsEmojiSmile className="cursor-pointer" />
                  <ImAttachment />
                  <input
                    className="py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]"
                    type="text"
                    onChange={(e) => setContent(e.target.value)}
                    placeholder="メッセージを入力"
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
};

export default HomePage;
