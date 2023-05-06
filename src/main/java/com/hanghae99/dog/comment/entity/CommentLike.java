package com.hanghae99.dog.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String username;

    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name="comment_id", nullable = false)
    private Comment comment;


    public CommentLike(String username,Comment comment) { //좋아요

        this.username = username;
        this.comment = comment;
    }

}
