package com.hanghae99.dog.image.repository;

import com.hanghae99.dog.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByAnimal_AnimalNo(Long animalNo);

    Optional<Image> findByUrl(String imageUrl);
}
