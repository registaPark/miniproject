package com.hanghae99.dog.consulting.dto;


import com.hanghae99.dog.consulting.entity.Consulting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingResponseDto {
    private String day;
    private String time;

    public ConsultingResponseDto(Consulting consulting) {
        this.day = consulting.getDay();
        this.time = consulting.getTime();
    }

}
