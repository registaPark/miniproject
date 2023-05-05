package com.hanghae99.dog.animal.dto;

import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.image.dto.ImageResponseDto;
import com.hanghae99.dog.image.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AnimalResponseDto {
    private Long animalNo;
    private String name;
    private LocalDate entrance_date;
    private String species;
    private String breed;
    private String sex;
    private String age;
    private Float weight;
    private String adpStatus;
    private String tmpr;
    private String introduceUrl;
    private List<ImageResponseDto> images;
    @Builder
    public AnimalResponseDto(Long animalNo, String name, LocalDate entrance_date, String species, String breed, String sex, String age, Float weight, String adpStatus,String tmpr,String introduceUrl, List<ImageResponseDto> images) {
        this.animalNo = animalNo;
        this.name = name;
        this.entrance_date = entrance_date;
        this.species = species;
        this.breed = breed;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.adpStatus = adpStatus;
        this.tmpr = tmpr;
        this.introduceUrl = introduceUrl;
        this.images = images;
    }
    public static AnimalResponseDto from(Animal animal){
        List<ImageResponseDto> images = animal.getImages().stream().map(i -> new ImageResponseDto(i)).collect(Collectors.toList());
        return AnimalResponseDto
                .builder()
                .animalNo(animal.getAnimalNo())
                .name(animal.getName())
                .entrance_date(animal.getEntrance_date())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .sex(animal.getSex())
                .age(animal.getAge())
                .weight(animal.getWeight())
                .adpStatus(animal.getAdpStatus())
                .images(images)
                .tmpr(animal.getTmpr())
                .introduceUrl(animal.getIntroduceUrl())
                .build();
    }
}
