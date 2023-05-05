package com.hanghae99.dog.image.entity;

import com.hanghae99.dog.animal.entity.Animal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
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
        animal.getImages().add(this);
    }
}
