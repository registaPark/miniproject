package com.hanghae99.dog.animal.repository;


import com.hanghae99.dog.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
    List<Animal> findAll();
    Optional<Animal> findById(Long id);
}
