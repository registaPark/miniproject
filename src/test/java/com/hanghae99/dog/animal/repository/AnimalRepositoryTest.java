package com.hanghae99.dog.animal.repository;

import com.hanghae99.dog.animal.entity.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional
class AnimalRepositoryTest {
    @Autowired
    private AnimalRepository animalRepository;


    @Test
    @DisplayName("유기동물 저장 테스트")
    public void whenFindAllAnimal_thenReturnAnimals(){
        //given
        Animal animal = Animal.builder()
                .animalNo(1L)
                .age("10")
                .sex("M")
                .breed("말티즈")
                .build();
        animalRepository.save(animal);
        animalRepository.flush();
        //when
        List<Animal> findAnimals = animalRepository.findAll();
        //then
        assertThat(findAnimals.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유기동물 id값으로 찾기 테스트")
    public void whenFindByAnimalNo_thenReturnAnimal(){
        //given
        Animal animal = Animal.builder()
                .animalNo(1L)
                .age("10")
                .sex("M")
                .breed("말티즈")
                .build();
        animalRepository.save(animal);
        animalRepository.flush();
        //when
        Optional<Animal> findAnimalById = animalRepository.findById(1L);
        //then
        assertThat(findAnimalById.isPresent()).isEqualTo(true);
        assertThat(findAnimalById.get().getAnimalNo()).isEqualTo(1L);
    }
    @Test
    @DisplayName("유기동물 id값으로 찾기 실패 테스트")
    public void whenFindByAnimalNo_thenFail(){
        //given
        Animal animal = Animal.builder()
                .animalNo(1L)
                .age("10")
                .sex("M")
                .breed("말티즈")
                .build();
        animalRepository.save(animal);
        animalRepository.flush();
        //when
        Optional<Animal> findAnimalById = animalRepository.findById(2L);
        //then
        assertThat(findAnimalById.isPresent()).isEqualTo(false);
    }

}