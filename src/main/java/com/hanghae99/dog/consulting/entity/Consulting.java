package com.hanghae99.dog.consulting.entity;

import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.user.Entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String username;

    private String days;
    private String time;
    private String phoneNumber;

    public Consulting(ConsultingRequestsDto dto, User user) {
        this.user = user;
        this.username = dto.getUsername();
        this.days = dto.getDays();
        this.time = dto.getTime();
        this.phoneNumber = dto.getPhoneNumber();
    }
    public Consulting(ConsultingRequestsDto dto) {
        this.username = dto.getUsername();
        this.days = dto.getDays();
        this.time = dto.getTime();
        this.phoneNumber = dto.getPhoneNumber();
    }


}



