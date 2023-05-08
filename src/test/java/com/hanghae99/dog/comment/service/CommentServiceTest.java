//package com.hanghae99.dog.comment.service;
//
//import com.hanghae99.dog.comment.dto.*;
//import com.hanghae99.dog.comment.entity.Comment;
//import com.hanghae99.dog.comment.entity.CommentLike;
//import com.hanghae99.dog.comment.repository.CommentLikeRepository;
//import com.hanghae99.dog.comment.repository.CommentRepository;
//import com.hanghae99.dog.animal.entity.Animal;
//import com.hanghae99.dog.animal.repository.AnimalRepository;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class CommentServiceTest {
//
//    @MockBean
//    private AnimalRepository animalRepository;
//
//    @MockBean
//    private CommentRepository commentRepository;
//
//    @MockBean
//    private CommentLikeRepository commentLikeRepository;
//
//    @Autowired
//    private CommentService commentService;
//
//    private static CmtRequestDto requestDto;
//
//    private static Animal animal;
//
//    private static Comment comment;
//
//    private static CommentLike commentLike;
//
//    private static List<Comment> commentList;
//
//    private static List<CmtResponseDto> commentResponseDtoList;
//
//    private static List<AllResponseDto> allResponseDtoList;
//
//    @BeforeAll
//    static void setUp() {
//        requestDto = new CmtRequestDto("username", "content", 1L);
//        animal = new Animal(1L, "title", "content", "image", "kind", "sex", 1, "location", 2);
//        comment = new Comment(1L, "username", "content", 0, animal);
//        commentLike = new CommentLike(1L, "username", comment);
//        commentList = new ArrayList<>();
//        commentList.add(comment);
//        commentResponseDtoList = new ArrayList<>();
//        commentResponseDtoList.add(new CmtResponseDto(comment));
//        allResponseDtoList = new ArrayList<>();
//        allResponseDtoList.add(new AllResponseDto(animal, commentResponseDtoList));
//    }
//
//    @Test
//    @DisplayName("댓글 작성")
//    void addCommentTest() {
//        // given
//        when(animalRepository.findById(anyLong())).thenReturn(Optional.ofNullable(animal));
//        when(commentRepository.saveAndFlush(any(Comment.class))).thenReturn(comment);
//
//        // when
//        CmtResponseDto responseDto = commentService.addComment(requestDto);
//
//        // then
//        assertEquals(responseDto.getContent(), comment.getContent());
//    }
//
//    @Test
//    @DisplayName("댓글 수정 - 작성자인 경우")
//    void updateCommentTestAuthor() {
//        // given
//        when(commentRepository.findByIdAndUsername(anyLong(), any(String.class))).thenReturn(Optional.ofNullable(comment));
//        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
//
//        // when
//        ResponseEntity responseEntity = commentService.updateComment(comment.getId(), requestDto);
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("댓글 수정 - 작성자가 아닌 경우")
//    void updateCommentTestNotAuthor() {
//        // given
//        Long commentId = 1L;
//        String username = "notAuthor";
//        CmtRequestDto requestDto = new CmtRequestDto(username, "수정된 댓글");
//
//        Comment comment = new Comment("작성자", "내용", 0);
//        comment.setId(commentId);
//
//        given(commentRepository.findByIdAndUsername(commentId, username)).willReturn(Optional.empty());
//
//        // when
//        ResponseEntity<?> response = commentService.updateComment(commentId, requestDto);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//}
