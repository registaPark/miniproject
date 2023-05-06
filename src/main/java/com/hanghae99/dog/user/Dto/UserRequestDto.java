package com.hanghae99.dog.user.Dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String username;
    private String password;
    private boolean admin;
    private String adminToken;
}
