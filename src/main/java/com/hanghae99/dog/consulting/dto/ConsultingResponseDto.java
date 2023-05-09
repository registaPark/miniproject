package com.hanghae99.dog.consulting.dto;


import com.hanghae99.dog.consulting.entity.Consulting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingResponseDto {

    private String days;

    private String time;

    public ConsultingResponseDto(Consulting consulting) {
        this.days = consulting.getDays();
        this.time = consulting.getTime();
    }
    public String getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }
}
