package com.hanghae99.dog.comment.service;

import com.hanghae99.dog.comment.dto.*;
import com.hanghae99.dog.comment.entity.Comment;
import com.hanghae99.dog.comment.entity.CommentLike;
import com.hanghae99.dog.comment.repository.CommentLikeRepository;
import com.hanghae99.dog.comment.repository.CommentRepository;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
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
    public CmtResponseDto addComment(CmtRequestDto cmtRequestDto) {

        Animal animal = animalRepository.findById(cmtRequestDto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        Comment comment = new Comment(cmtRequestDto, animal);
        commentRepository.saveAndFlush(comment);
        return new CmtResponseDto(comment);

    }

    //댓글 수정
    @Transactional
    public ResponseEntity updateComment(Long id, CmtRequestDto cmtRequestDto) {

        Comment comment = commentRepository.findByIdAndUsername(id, cmtRequestDto.getUsername()).orElseThrow(
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

    //댓글 삭제
    @Transactional
    public ResponseEntity deleteComment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.")
        );

        commentRepository.delete(comment);
        MsgResponseDto msgResponseDto = new MsgResponseDto("댓글 삭제 성공", 200);
        return ResponseEntity.status(200).body(msgResponseDto);
    }



    // 댓글 좋아요 API
    @Transactional
    public ResponseEntity CommentGood(Long commentId, CmtRequestDto cmtRequestDto) {
        // 요청받은 commentId 값을 가진 댓글을 조회합니다.
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 현재 사용자가 해당 댓글에 대해 좋아요를 이미 눌렀는지 조회합니다.
        CommentLike commentLike = commentLikeRepository.findByUsernameAndComment(cmtRequestDto.getUsername(), comment);

        if (commentLike == null) { // 사용자가 해당 댓글에 대해 좋아요를 처음 요청하는 경우
            // CommentGood 테이블에 새로운 레코드를 생성하고, Comment의 좋아요 수를 1 증가시킵니다.
            commentLikeRepository.save(new CommentLike(cmtRequestDto.getUsername(), comment));
            comment.setLike(comment.getCommentlike() + 1);
            commentRepository.save(comment);

            // 요청이 성공한 경우, 클라이언트에게 "좋아요 성공" 메시지와 상태코드 200을 반환합니다.
            MsgResponseDto msgResponseDto = new MsgResponseDto("좋아요 성공", 200);
            return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
        } else { // 사용자가 이미 해당 댓글에 대해 좋아요를 누른 경우
            // CommentGood 테이블에서 해당 레코드를 삭제하고, Comment의 좋아요 수를 1 감소시킵니다.
            commentLikeRepository.delete(commentLike);
            comment.setLike(comment.getCommentlike() - 1);
            commentRepository.save(comment);

            // 요청이 성공한 경우, 클라이언트에게 "좋아요 삭제 성공" 메시지와 상태코드 200을 반환합니다.
            MsgResponseDto msgResponseDto = new MsgResponseDto("좋아요 삭제 성공", 200);
            return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
        }
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<AllResponseDto> getPost() {
        List<AllResponseDto> allResponseDto = new ArrayList();
        List<Animal> animalList = animalRepository.findAll();
        for(Animal animal : animalList){
            List<CmtResponseDto> commentResponseDto;
            commentResponseDto = commentRepository.findAllCommentByPostId(animal.getAnimalNo());
            allResponseDto.add(new AllResponseDto(animal, commentResponseDto));
        }
        return allResponseDto;
    }
}
