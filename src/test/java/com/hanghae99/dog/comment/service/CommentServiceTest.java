
package com.hanghae99.dog.comment.service;

import com.hanghae99.dog.comment.dto.*;
import com.hanghae99.dog.comment.entity.Comment;
import com.hanghae99.dog.comment.repository.CommentRepository;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.image.repository.ImageRepository;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @MockBean
    private AnimalRepository animalRepository;

    @MockBean
    private CommentRepository commentRepository;


    @MockBean
    private ImageRepository imageRepository;



    @Autowired
    private CommentService commentService;

    private static User user;

    private static UserRoleEnum userRoleEnum;
    private static CmtRequestDto requestDto;

    private static Animal animal;

    private static Comment comment;

    private static List<Comment> commentList;

    private static List<CmtResponseDto> commentResponseDtoList;

    private static List<AllResponseDto> allResponseDtoList;

    @BeforeAll
    static void setUp() {

        animal = new Animal(486L,"강아지",LocalDate.parse("2022-05-08"),"image","코기","sex","1",2.0F, "location","tmpr","introduceUrl");
        requestDto = new CmtRequestDto();
        requestDto.setAnimal_no(486L);
        requestDto.setContent("content");

        user = new User("username","123123", userRoleEnum);

        comment = new Comment(requestDto,animal,"username");
        commentList = new ArrayList<>();
        commentList.add(comment);

        commentResponseDtoList = new ArrayList<>();
        commentResponseDtoList.add(new CmtResponseDto(comment));

        allResponseDtoList = new ArrayList<>();
        allResponseDtoList.add(new AllResponseDto(animal, commentResponseDtoList));
    }


    @Test
    @DisplayName("댓글 작성")
    void addCommentTest() {
        // given
        animalRepository.saveAndFlush(animal);
        Animal animal1 = new Animal(486L,"강아지",LocalDate.parse("2022-05-08"),"image","코기","sex","1",2.0F, "location","tmpr","introduceUrl");


        CmtRequestDto requestDto = new CmtRequestDto();
        requestDto.setAnimal_no(animal1.getAnimalNo());
        requestDto.setContent("test comment");

        when(animalRepository.findById(animal1.getAnimalNo())).thenReturn(Optional.of(animal1));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // when
        commentService.addComment(requestDto, user);

        // then
        assertEquals(requestDto.getContent(), "test comment");
    }


    





    @Test
    @DisplayName("댓글 수정")
    void updateCommentTestAuthor() {
        // given
        animalRepository.saveAndFlush(animal);
        Animal animal1 = new Animal(486L,"강아지",LocalDate.parse("2022-05-08"),"image","코기","sex","1",2.0F, "location","tmpr","introduceUrl");

        CmtRequestDto requestDto = new CmtRequestDto();
        requestDto.setAnimal_no(animal1.getAnimalNo());
        requestDto.setContent("test comment");

        User user = new User("username","123123", userRoleEnum);

        Comment comment1 = new Comment(requestDto, animal1, user.getUsername());
        given(commentRepository.save(any())).willReturn(comment1);
        given(commentRepository.findByIdAndUsername(any(),any())).willReturn(Optional.of(comment1));



        // Update the comment
        CmtRequestDto updateRequestDto = new CmtRequestDto();
        updateRequestDto.setContent("updated comment");
        ResponseEntity<CmtResponseDto> updateResponseEntity = commentService.updateComment(comment1.getId(), updateRequestDto, user);

        // then
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
        assertEquals("updated comment", updateResponseEntity.getBody().getContent());
    }




    private Animal createAnimal(Long animalNo , String breed) {
        return Animal.builder()
                .animalNo(animalNo)
                .age("10")
                .sex("M")
                .breed(breed)
                .build();
    }
}
