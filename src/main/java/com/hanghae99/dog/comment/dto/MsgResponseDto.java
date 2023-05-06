package com.hanghae99.dog.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MsgResponseDto { //PostResponseDto에 있던 필드와 생성자를 별도로 만들어줌
    private String msg;
    private int statusCode;
}
