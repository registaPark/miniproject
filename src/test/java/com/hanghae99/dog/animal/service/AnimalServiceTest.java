package com.hanghae99.dog.animal.service;

import com.hanghae99.dog.animal.dto.AnimalResponseDto;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.comment.dto.AllResponseDto;
import com.hanghae99.dog.comment.dto.CmtResponseDto;
import com.hanghae99.dog.comment.entity.Comment;
import com.hanghae99.dog.comment.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    @DisplayName("유기동물 리스트 찾기 테스트")
    public void findAnimalList(){
        //given
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(createAnimal(1L,"말티즈"));
        animals.add(createAnimal(2L,"포메라니안"));
        //when
        when(animalRepository.findAll()).thenReturn(animals);
        //then
        List<AnimalResponseDto> animalList = animalService.findAnimalList();
        assertThat(animalList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유기동물 리스트 찾기 테스트")
    public void whenFindAnimalById(){
        //given
        Animal animal = createAnimal(1L, "말티즈");
        ArrayList<CmtResponseDto> cmtResponseList = new ArrayList<>();
        cmtResponseList.add(new CmtResponseDto(new Comment()));
        //when
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(commentRepository.findAllCommentByAnimal_id(1L)).thenReturn(cmtResponseList);
        //then
        AllResponseDto findAnimalById = animalService.findAnimalById(1L);
        assertThat(findAnimalById.getAnimalNo()).isEqualTo(1L);
        assertThat(findAnimalById.getComments().size()).isEqualTo(1);
    }
    @Test
    @DisplayName("유기동물 리스트 찾기 실패 테스트")
    public void whenFindAnimalById_thenFail(){
        //given
        Animal animal = createAnimal(1L, "말티즈");
        //when
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(()->animalService.findAnimalById(1L)).isInstanceOf(IllegalArgumentException.class);
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