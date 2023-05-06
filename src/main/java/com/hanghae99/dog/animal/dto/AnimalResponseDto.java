package com.hanghae99.dog.animal.dto;

import com.hanghae99.dog.animal.entity.Animal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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
    @Builder
    public AnimalResponseDto(Long animalNo, String name, LocalDate entrance_date, String species, String breed, String sex, String age, Float weight, String adpStatus,String tmpr,String introduceUrl) {
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
    }
    public static AnimalResponseDto from(Animal animal){
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
                .tmpr(animal.getTmpr())
                .introduceUrl(animal.getIntroduceUrl())
                .build();
    }
}
