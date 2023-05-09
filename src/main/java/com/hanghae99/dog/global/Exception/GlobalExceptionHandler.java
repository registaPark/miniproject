package com.hanghae99.dog.global.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.BindException;


@Slf4j
@RestControllerAdvice //프로젝트 전역에서 발생하는 모든 예외를 잡아준다. 모든 예외를 잡은 후에, Exception 종류별로 메소드를 공통 처리할 수 있다.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /*
     * Developer Custom Exception: 직접 정의한 RestApiException 에러 클래스에 대한 예외 처리
     */
    @ExceptionHandler({CustomException.class}) // Controller에서만 가능하지만 Service를 Controller에서 호출에서 사용하기 때문에 Service도 가능
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error(e.getErrorCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


//    컨트롤러에 @PreAuthorize 어노테이션을 사용하여 인증된 사용자가 아닐경우 바디에 "인증되지 않은 사용자입니다."을 반환하는 예외처리 핸들러
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ErrorResponse.toResponseEntity(ErrorCode.UNAUTHORIZED_USER);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("{");
            builder.append(fieldError.getField());
            builder.append("}(은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: {");
            builder.append(fieldError.getRejectedValue());
            builder.append("} ");
        }
        log.error(builder.toString());
        return handleExceptionInternal(ex, builder.toString(), headers, status, request);
    }
}