package com.hanghae99.dog.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String username;
    private String password;
    private boolean admin;
    private String adminToken;
}
