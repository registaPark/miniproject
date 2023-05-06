package com.hanghae99.dog.comment.dto;

import com.hanghae99.dog.animal.entity.Animal;
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


    public AnimalResponseDto(Animal animal) {
        this.animalNo = animal.getAnimalNo();
        this.name = animal.getName();
        this.entrance_date = animal.getEntrance_date();
        this.species = animal.getSpecies();
        this.breed = animal.getBreed();
        this.sex = animal.getSex();
        this.age = animal.getAge();
        this.weight = animal.getWeight();
        this.adpStatus = animal.getAdpStatus();

    }


}
