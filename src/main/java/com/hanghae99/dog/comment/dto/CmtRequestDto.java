package com.hanghae99.dog.comment.dto;

import lombok.Getter;

@Getter
public class CmtRequestDto {
    private Long postId;
    private String content;
    private String  username;
}
