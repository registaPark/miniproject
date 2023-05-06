package com.hanghae99.dog.consulting.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelConsultingResponseDto {
    private String msg;
    private int statusCode;

    public CancelConsultingResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}