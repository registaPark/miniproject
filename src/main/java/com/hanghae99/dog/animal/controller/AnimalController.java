package com.hanghae99.dog.animal.controller;

import com.hanghae99.dog.animal.dto.AnimalResponseDto;
import com.hanghae99.dog.animal.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    @GetMapping("/list") // AniamlList 가져오기
    public ResponseEntity<List<AnimalResponseDto>> findAnimalList(){
        return ResponseEntity.ok(animalService.findAnimalList());
    }

    @GetMapping("/detail/{id}") //AnimalNo값으로 가져오기
    public ResponseEntity<AnimalResponseDto> findByAnimalId(@PathVariable Long id){
        return ResponseEntity.ok(animalService.findAnimalById(id));
    }
}
