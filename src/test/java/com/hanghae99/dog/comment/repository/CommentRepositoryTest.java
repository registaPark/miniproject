package com.hanghae99.dog.comment.repository;

import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.comment.dto.CmtRequestDto;
import com.hanghae99.dog.comment.entity.Comment;

import com.hanghae99.dog.image.entity.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void saveCommentTest() {

        // given
        LocalDate entrance_date = LocalDate.ofEpochDay(2018-06-28);
        Animal animal = new Animal(
                486L,
                "강아지",
                entrance_date,
                "몰라",
                "코기",
                "남자",
                "5",
                4.0F,
                "몰라",
                "몰라",
                "몰라"
        );
        animalRepository.save(animal);

        CmtRequestDto cmtRequestDto = new CmtRequestDto();
        cmtRequestDto.setContent("test comment");
        cmtRequestDto.setAnimal_no(468L);

        String username = "wkdrldnd";

        Comment comment = new Comment(cmtRequestDto, animal, username);

// when
        Comment savedComment = commentRepository.save(comment);


        // then
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getUsername()).isEqualTo("wkdrldnd");
        assertThat(savedComment.getContent()).isEqualTo("test comment");
    }

    @Test
    public void findByCommentIdTest() {

        // given
        LocalDate entrance_date = LocalDate.ofEpochDay(2018-06-28);

        Animal animal = new Animal(
                486L,
                "강아지",
                entrance_date,
                "몰라",
                "코기",
                "남자",
                "5",
                4.0F,
                "몰라",
                "몰라",
                "몰라"
        );
        animalRepository.save(animal);

        CmtRequestDto cmtRequestDto = new CmtRequestDto();
        cmtRequestDto.setContent("test comment");
        String username = "wkdrldnd";

        Comment comment = new Comment(cmtRequestDto, animal, username);
        Comment comment1 = commentRepository.save(comment);


        // when
        Optional<Comment> resultComment = commentRepository.findById(comment1.getId());

        // then
        assertThat(resultComment.isPresent()).isTrue();
        assertThat(resultComment.get().getId()).isEqualTo(comment1.getId());
        assertThat(resultComment.get().getUsername()).isEqualTo("wkdrldnd");
        assertThat(resultComment.get().getContent()).isEqualTo("test comment");
    }
}
