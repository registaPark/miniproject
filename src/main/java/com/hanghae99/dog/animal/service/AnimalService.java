package com.hanghae99.dog.animal.service;

import com.hanghae99.dog.animal.dto.AnimalResponseDto;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.comment.dto.AllResponseDto;
import com.hanghae99.dog.comment.dto.CmtResponseDto;
import com.hanghae99.dog.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CommentRepository commentRepository;

    public List<AnimalResponseDto> findAnimalList(){ //Animal 리스트조회
        return animalRepository.findAll().stream().map(a->AnimalResponseDto.from(a)).collect(Collectors.toList());
    }

    public AllResponseDto findAnimalById(Long id){ //AnimalNo로 단건조회
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유기견을 찾을 수 없습니다."));
        List<CmtResponseDto> allCommentByPostId = commentRepository.findAllCommentByPostId(id);
        AllResponseDto allResponseDto = new AllResponseDto(animal, allCommentByPostId);
        return  allResponseDto;
    }
}
