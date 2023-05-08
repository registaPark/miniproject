//package com.hanghae99.dog.comment.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hanghae99.dog.animal.entity.Animal;
//import com.hanghae99.dog.comment.dto.AllResponseDto;
//import com.hanghae99.dog.comment.dto.CmtRequestDto;
//import com.hanghae99.dog.comment.dto.CmtResponseDto;
//import com.hanghae99.dog.comment.entity.Comment;
//import com.hanghae99.dog.comment.service.CommentService;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CommentController.class)
//public class CommentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CommentService commentService;
//
//    @Test
//    public void addCommentTest() throws Exception {
//        // given
//        CmtRequestDto cmtRequestDto = new CmtRequestDto();
//        cmtRequestDto.setContent("test comment");
//        cmtRequestDto.setAnimal_no(468L);
//
//        LocalDate entranceDate = LocalDate.of(2018, 6, 28);
//        Animal animal = new Animal(1L, "dog", entranceDate, "test shelter", "Corgi", "male", "5", 4.0F, "test url", "test description", "test status");
//
//        String username = "wkdrldnd";
//
//        Comment comment = new Comment(cmtRequestDto, animal, String username);
//        CmtResponseDto expectedResponseDto = new CmtResponseDto(comment);
//
////        Mockito.when(commentService.addComment()).thenReturn(expectedResponseDto);
//
//        // when
//        MvcResult result = mockMvc.perform(post("/api/post/comment")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(cmtRequestDto)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // then
//        String actualJson = result.getResponse().getContentAsString();
//        if (!actualJson.isEmpty()) {
//            String expectedJson = new ObjectMapper().writeValueAsString(expectedResponseDto);
//            JSONAssert.assertEquals(expectedJson, actualJson, true);
//        }
//    }
//
//
//
//
//    @Test
//    public void updateCommentTest() throws Exception {
//        // given
//        Long id = 1L;
//        CmtRequestDto cmtRequestDto = new CmtRequestDto();
//        cmtRequestDto.setContent("test comment");
//        cmtRequestDto.setAnimal_no(468L);
//
//        LocalDate entranceDate = LocalDate.of(2018, 6, 28);
//        Animal animal = new Animal(1309L, "dog", entranceDate, "test shelter", "Corgi", "male", "5", 4.0F, "test url", "test description", "test status");
//
//        String username = "wkdrldnd";
//
//        Comment comment = new Comment(cmtRequestDto, animal, username);
//
//        CmtResponseDto updatedCmtResponseDto = new CmtResponseDto(comment);
//        updatedCmtResponseDto.setContent("Comment updated successfully");
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<CmtResponseDto> responseEntity = new ResponseEntity<>(updatedCmtResponseDto, headers, HttpStatus.OK);
//
//        when(commentService.updateComment(id)).thenReturn(responseEntity);
//
//        // when and then
//        MvcResult mvcResult = mockMvc.perform(put("/api/post/comment/" + id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(cmtRequestDto)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String expectedJson = "{\"username\":\"test_username\",\"content\":\"Comment updated successfully\"}";
//        String actualJson = mvcResult.getResponse().getContentAsString();
//        JSONAssert.assertEquals(expectedJson, actualJson, true);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    @Test
//    public void deleteCommentTest() throws Exception {
//        Long id = 1L;
//
//        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
//
//        when(commentService.deleteComment(id)).thenReturn(responseEntity);
//
//        mockMvc.perform(delete("/api/post/comment/" + id)
//                        .contentType(MediaType.APPLICATION_JSON));
////                .andExpect(status().isOk())
////                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseEntity)));
//    }
//
//    @Test
//    public void commentGoodTest() throws Exception {
//        Long commentId = 1L;
//
//        CmtRequestDto cmtRequestDto = new CmtRequestDto();
//        cmtRequestDto.setContent("asd");
//        cmtRequestDto.setUsername("wkdrldnd");
//        cmtRequestDto.setPostId(1L);
//        // cmtRequestDto에 필요한 값들을 채워줍니다.
//
//        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
//
//        when(commentService.CommentGood(commentId, cmtRequestDto)).thenReturn(responseEntity);
//
//        mockMvc.perform(post("/api/post/like/"+ commentId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(cmtRequestDto)))
//                .andExpect(status().isOk());
////                .andExpect(content().string(new ObjectMapper().writeValueAsString(responseEntity)));
//    }
//
//
//
//}