package com.hanghae99.dog.animal.controller;

import com.hanghae99.dog.animal.dto.AnimalResponseDto;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.service.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AnimalController.class)
class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Test
    @DisplayName("유기동물 목록 컨트롤러 테스트")
    public void whenGetAnimalList_thenReturnAnimalList() throws Exception {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(createAnimal(1L,"말티즈"));
        animals.add(createAnimal(2L,"포메라니안"));
        List<AnimalResponseDto> animalResponseDtos =
                animals.
                stream().map(a -> AnimalResponseDto.from(a)).collect(Collectors.toList());
        when(animalService.findAnimalList()).thenReturn(animalResponseDtos);
        mockMvc.perform(get("/api/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].animalNo").value(1L));
    }

    @Test
    @DisplayName("유기동물 세부목록 컨트롤러 테스트")
    public void whenGetAnimalById_thenReturnAnimal() throws Exception {
        long animalId = 1L;
        Animal animal = createAnimal(animalId, "말티즈");
        AnimalResponseDto animalResponseDto = AnimalResponseDto.from(animal);
        when(animalService.findAnimalById(animalId)).thenReturn(animalResponseDto);
        mockMvc.perform(get("/api/detail/{id}",animalId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.animalNo").value(1L));
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