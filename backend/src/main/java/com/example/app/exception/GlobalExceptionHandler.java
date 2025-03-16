package com.example.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * アプリケーション全体で発生する例外を一元管理するクラス
 * - 各種例外ごとに適切なレスポンスを返す
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ユーザー関連の例外を処理
     * @param e UserException（ユーザー関連のカスタム例外）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（400 Bad Request）
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /**
     * メッセージ関連の例外を処理
     * @param e MessageException（メッセージ関連のカスタム例外）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（400 Bad Request）
     */
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /**
     * チャット関連の例外を処理
     * @param e ChatException（チャット関連のカスタム例外）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（400 Bad Request）
     */
    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /**
     * バリデーション失敗時の例外を処理
     * - フォーム入力などで必須項目が未入力の場合などに発生
     * @param e MethodArgumentNotValidException（バリデーションエラー）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（400 Bad Request）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,
            WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /**
     * 存在しないエンドポイントへのリクエスト時の例外を処理
     * - 404 Not Found エラーとして処理
     * @param e NoHandlerFoundException（存在しないURLへのアクセス）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（404 Not Found）
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> noHandlerFoundExceptionHandler(NoHandlerFoundException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * その他の予期しない例外を処理
     * - サーバー内部エラー（500 Internal Server Error）として処理
     * @param e Exception（その他の例外）
     * @param request WebRequest（発生したリクエスト情報）
     * @return エラー詳細（500 Internal Server Error）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

