import React from "react";
import { TbCircleDashed } from "react-icons/tb";
import { BiCommentDetail } from "react-icons/bi";
import { BsThreeDotsVertical } from "react-icons/bs";
import { Menu, MenuItem } from "@mui/material";
import { useNavigate } from "react-router-dom";

const ProfileSection = ({ auth, isProfile, isGroup, handleNavigate, handleClick, handleCreateGroup, handleLogout, handleClose, open, anchorEl }) => {
  const navigate = useNavigate();

  return (
    <div className="flex justify-between items-center p-3">
      {/* プロフィール画像とユーザー名の表示部分 */}
      <div onClick={handleNavigate} className="flex items-center space-x-3">
        <img className="rounded-full w-10 h-10 cursor-pointer" src={auth.reqUser?.profile || "https://cdn.pixabay.com/photo/2024/07/29/21/17/profile-8930641_640.png"} alt="profile" />
        <p>{auth.reqUser?.name}</p>
      </div>

      {/* アイコンの表示部分 */}
      <div className="space-x-3 text-2xl flex">
        {/* ステータスページへの遷移 */}
        <TbCircleDashed className="cursor-pointer" onClick={() => navigate("/status")} />

        {/* チャットアイコン */}
        <BiCommentDetail />

        {/* メニューアイコン */}
        <div>
          <BsThreeDotsVertical id="basic-button" aria-controls={open ? "basic-menu" : undefined} aria-haspopup="true" aria-expanded={open ? "true" : undefined} onClick={handleClick} className="cursor-pointer" />

          {/* メニューの表示 */}
          <Menu
            id="basic-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            MenuListProps={{
              "aria-labelledby": "basic-button",
            }}
          >
            {/* メニュー項目 */}
            <MenuItem onClick={handleNavigate}>プロフィール</MenuItem>
            <MenuItem onClick={handleCreateGroup}>新規チャットを作成</MenuItem>
            <MenuItem onClick={handleLogout}>ログアウト</MenuItem>
          </Menu>
        </div>
      </div>
    </div>
  );
};

export default ProfileSection;
