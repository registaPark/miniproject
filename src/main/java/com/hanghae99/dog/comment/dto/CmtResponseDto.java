package com.hanghae99.dog.comment.dto;

import com.hanghae99.dog.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CmtResponseDto {
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private String content;
    private Long id;
    private Long commentLike;


    public CmtResponseDto(Comment comment) {
//        this.createdAt = comment.getCreatedAt();
//        this.modifiedAt = comment.getModifiedAt();
        this.content = comment.getContent();
        this.id = comment.getId();
        this.commentLike = comment.getCommentlike();
    }


}
