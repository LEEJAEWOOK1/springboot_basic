package com.ex01.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthException {
    //비밀번호 틀렸을 시 예외 발생
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ProblemDetail> handlerBadCredentials(
            InvalidPasswordException invalidPasswordException){
        //예외 발생 body 설정
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("인증 실패");
        problemDetail.setDetail(invalidPasswordException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }
}
