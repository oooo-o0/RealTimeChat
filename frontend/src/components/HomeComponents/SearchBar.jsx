import React from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { BsFilter } from "react-icons/bs";

// 検索バーコンポーネント
const SearchBar = ({ querys, setQuerys, handleSearch }) => {
  return (
    <div className="relative flex justify-center items-center bg-white py-4 px-3">
      {/* 検索入力フィールド */}
      <input
        className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-9 py-3"
        type="text"
        placeholder="Search or Start new chat"
        onChange={(e) => {
          setQuerys(e.target.value); // 入力値を状態に反映
          handleSearch(e.target.value); // 検索処理を実行
        }}
        value={querys} // 入力値を表示
      />

      {/* 検索アイコン（入力フィールド内左側） */}
      <AiOutlineSearch className="left-5 top-8 absolute" />

      {/* フィルターアイコン */}
      <div>
        <BsFilter className="ml-4 text-3xl" />
      </div>
    </div>
  );
};

export default SearchBar;
