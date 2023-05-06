package com.hanghae99.dog.comment.entity;


import lombok.Getter;

import javax.persistence.Column;

@Getter
public class PostLikeRequestDto {

    @Column
    private String username;

}
