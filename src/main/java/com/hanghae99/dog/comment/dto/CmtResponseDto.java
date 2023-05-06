package com.hanghae99.dog.comment.dto;

import com.hanghae99.dog.comment.entity.Comment;
import lombok.Getter;



@Getter
public class CmtResponseDto {
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private String content;
    private String username;
    private Long id;
    private Long commentLike;


    public CmtResponseDto(Comment comment) {
//        this.createdAt = comment.getCreatedAt();
//        this.modifiedAt = comment.getModifiedAt();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.id = comment.getId();
        this.commentLike = comment.getCommentlike();
    }
}
