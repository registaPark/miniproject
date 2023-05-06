package com.hanghae99.dog.animal.repository;


import com.hanghae99.dog.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
    @Query("select a from Animal a left join fetch a.images i") // image들도 같이 가져오기 위해 쿼리 작성
    List<Animal> findAll();
    @Query("select a from Animal a left join fetch a.images i where a.animalNo=:id") // image도 한번에 가져오기 위해 쿼리 작성
    Optional<Animal> findById(@Param("id") Long id);
}
