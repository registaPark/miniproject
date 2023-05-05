package com.hanghae99.dog.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    private String url;
    @Builder
    public Image(Animal animal, String url) {
        this.animal = animal;
        this.url = url;
    }
}
