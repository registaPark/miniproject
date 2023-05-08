package com.hanghae99.dog.comment.service;

import com.hanghae99.dog.comment.dto.*;
import com.hanghae99.dog.comment.entity.Comment;
import com.hanghae99.dog.comment.entity.CommentLike;
import com.hanghae99.dog.comment.repository.CommentLikeRepository;
import com.hanghae99.dog.comment.repository.CommentRepository;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final AnimalRepository animalRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;


    //댓글 작성
    @Transactional
    public ResponseEntity addComment(CmtRequestDto cmtRequestDto, User user) {


        Animal animal = animalRepository.findById(cmtRequestDto.getAnimal_no()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );


        Comment comment = new Comment(cmtRequestDto, animal, user.getUsername());
        commentRepository.saveAndFlush(comment);
        CmtResponseDto cmtResponseDto = new CmtResponseDto(comment);
        return ResponseEntity.status(200).body(cmtResponseDto);

    }

    //댓글 수정
    @Transactional
    public ResponseEntity updateComment(Long id, CmtRequestDto cmtRequestDto, User user) {

        UserRoleEnum userRoleEnum = user.getRole();
        System.out.println("role = " + userRoleEnum);
        if( userRoleEnum == UserRoleEnum.ADMIN) {
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
            );
            comment.update(cmtRequestDto);
            CmtResponseDto cmtResponseDto = new CmtResponseDto(comment);
            return ResponseEntity.status(200).body(cmtResponseDto);
        }else {


            Comment comment = commentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                    () -> new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.")
            );
            if (comment == null) {
                MsgResponseDto msgResponseDto = new MsgResponseDto("작성자만 삭제/수정할 수 있습니다.", 400);
                return ResponseEntity.status(400).body(msgResponseDto);
            } else {
                comment.update(cmtRequestDto);
                CmtResponseDto cmtResponseDto = new CmtResponseDto(comment);
                return ResponseEntity.status(200).body(cmtResponseDto);
            }
        }
    }

    //댓글 삭제
    @Transactional
    public ResponseEntity deleteComment(Long id, User user) {

        //사용자 권환 확인 (ADMUN인지 아닌지)
        UserRoleEnum userRoleEnum = user.getRole();
        System.out.println("role = " + userRoleEnum);
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
            );
            commentLikeRepository.deleteByComment(comment);
            commentRepository.delete(comment);
            MsgResponseDto msgResponseDto = new MsgResponseDto("댓글 삭제 성공", 200);
            return ResponseEntity.status(200).body(msgResponseDto);
        } else {

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.")
            );

            commentRepository.delete(comment);
            MsgResponseDto msgResponseDto = new MsgResponseDto("댓글 삭제 성공", 200);
            return ResponseEntity.status(200).body(msgResponseDto);
        }
    }



    // 댓글 좋아요 API
    @Transactional
    public ResponseEntity CommentGood(Long commentId, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));


        CommentLike commentLike = commentLikeRepository.findByUsernameAndComment(userDetails.getUsername(), comment);

        if (commentLike == null) {

            commentLikeRepository.save(new CommentLike(userDetails.getUsername(), comment));
            comment.setLike(comment.getCommentlike() + 1);
            commentRepository.save(comment);


            MsgResponseDto msgResponseDto = new MsgResponseDto("좋아요 성공", 200);
            return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
        } else {

            commentLikeRepository.delete(commentLike);
            comment.setLike(comment.getCommentlike() - 1);
            commentRepository.save(comment);


            MsgResponseDto msgResponseDto = new MsgResponseDto("좋아요 삭제 성공", 200);
            return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
        }
    }

//    //선택 게시글 조회
//    @Transactional(readOnly = true)
//    public AllResponseDto getOneAnimal(Long id) {
//
//        Animal animal = animalRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
//        );
//        List<CmtResponseDto> comments = commentRepository.findAllComment(id);
//
//        return new AllResponseDto(animal,comments);
//    }
//
//    //게시글 전체 조회
//    @Transactional(readOnly = true)
//    public List<AllResponseDto> getPost() {
//        List<AllResponseDto> allResponseDto = new ArrayList();
//        List<Animal> animalList = animalRepository.findAll();
//        for(Animal animal : animalList){
//            List<CmtResponseDto> commentResponseDto;
//            commentResponseDto = commentRepository.findAllCommentByAnimal_id(animal.getAnimalNo());
//            allResponseDto.add(new AllResponseDto(animal, commentResponseDto));
//        }
//        return allResponseDto;
//    }

}
