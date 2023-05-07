package com.hanghae99.dog.user.Dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    private int statusCode;
    private String msg;

    public StatusResponseDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
