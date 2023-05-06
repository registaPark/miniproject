package com.hanghae99.dog.comment.repository;


import com.hanghae99.dog.comment.dto.CmtResponseDto;
import com.hanghae99.dog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    Optional<Comment> findByIdAndUsername(Long id, String username);


//    List<CmtResponseDto> findAllByAnimalAnimalNo(Long animalNo);

    @Query("select c from Comment c where c.animal.animalNo = :id")
    List<CmtResponseDto> findAllCommentByPostId(@Param("id") Long id);

    @Query("select c from Comment c where c.animal.animalNo = :id")
    List<CmtResponseDto> findAllComment(@Param("id") Long id);
}
