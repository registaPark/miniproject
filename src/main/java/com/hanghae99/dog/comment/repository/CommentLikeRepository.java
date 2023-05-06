package com.hanghae99.dog.comment.repository;

import com.hanghae99.dog.comment.entity.CommentLike;
import com.hanghae99.dog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    void deleteByComment(Comment comment);

    CommentLike findByUsernameAndComment(String username, Comment comment);
}
