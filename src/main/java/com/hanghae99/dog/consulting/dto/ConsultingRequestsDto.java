package com.hanghae99.dog.consulting.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Getter
@Setter
public class ConsultingRequestsDto {
    private String username;
    private String days;
    private String time;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 숫자여야 하며 010-1234-1234 형식이어야 합니다.")
    private String phoneNumber;
}
