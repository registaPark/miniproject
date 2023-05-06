package com.hanghae99.dog.consulting.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class ConsultingRequestsDto {
    private String username;
    private String day;
    private String time;
    private String phoneNumber;

}
