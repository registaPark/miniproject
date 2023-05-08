package com.hanghae99.dog.comment.controller;

import com.hanghae99.dog.comment.dto.AllResponseDto;
import com.hanghae99.dog.comment.dto.CmtRequestDto;
import com.hanghae99.dog.comment.dto.CmtResponseDto;

import com.hanghae99.dog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.hanghae99.dog.global.Security.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    //게시글 목록조회
//    @GetMapping("/posts")
//    public List<AllResponseDto> getPost(){
//        return commentService.getPost();
//    }

//    //게시글 상세조회
//    @CrossOrigin("*")
//    @GetMapping("/detail/{id}")
//    public AllResponseDto getOneAnimal(@PathVariable Long id){
//        return commentService.getOneAnimal(id);
//    }

    //댓글 작성
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody CmtRequestDto cmtRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.addComment(cmtRequestDto,userDetails.getUser());
    }

    //댓글 수정
    @PutMapping("/comment/{id}")
    public ResponseEntity updateComment(@PathVariable Long id, @RequestBody CmtRequestDto cmtRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, cmtRequestDto, userDetails.getUser());
    }


    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }

    //댓글 좋아요
    @PostMapping("/like/{id}")
    public ResponseEntity goodPost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.CommentGood(id, userDetails);
    }
}
