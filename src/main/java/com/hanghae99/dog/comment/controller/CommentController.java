package com.hanghae99.dog.comment.controller;

import com.hanghae99.dog.comment.dto.AllResponseDto;
import com.hanghae99.dog.comment.dto.CmtRequestDto;
import com.hanghae99.dog.comment.dto.CmtResponseDto;

import com.hanghae99.dog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //게시글 목록조회
    @GetMapping("/posts")
    public List<AllResponseDto> getPost(){
        return commentService.getPost();
    }

    //게시글 상세조회
    @GetMapping("/{id}")
    public AllResponseDto getPost(@PathVariable Long id){
        return commentService.getOnePost(id);
    }

    //댓글 작성
    @PostMapping("/comment")
    public CmtResponseDto addComment(@RequestBody CmtRequestDto cmtRequestDto){
        return commentService.addComment(cmtRequestDto);
    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CmtRequestDto cmtRequestDto) {
        return commentService.updateComment(id, cmtRequestDto);
    }


    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    //댓글 좋아요
    @PostMapping("/like/{id}")
    public ResponseEntity goodPost(@PathVariable Long id, @RequestBody CmtRequestDto cmtRequestDto){
        return commentService.CommentGood(id, cmtRequestDto);
    }
}
