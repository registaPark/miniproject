package com.hanghae99.dog.consulting.entity;

import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;


@Entity
@Table(name="consulting")
@Getter
@Setter
@NoArgsConstructor
public class Consulting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(name="\"day\"")
    private String day;
    private String time;
    private String phoneNumber;

    public Consulting(ConsultingRequestsDto dto) {
        this.username = dto.getUsername();
        this.day = dto.getDay();
        this.time = dto.getTime();
        this.phoneNumber = dto.getPhoneNumber();
    }


}



