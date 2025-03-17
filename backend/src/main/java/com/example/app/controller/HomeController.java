package com.example.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // REST APIのエンドポイントを定義するコントローラー
public class HomeController {

    /**
     * ルートパス "/" にGETリクエストがあった場合に実行
     * - "Welcome" というメッセージを返す
     * - HTTPステータス 200 OK を設定
     */
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<String>("Welcome", HttpStatus.OK);
    }

}
