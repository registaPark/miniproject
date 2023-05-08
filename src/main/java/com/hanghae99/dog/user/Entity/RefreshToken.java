package com.hanghae99.dog.user.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String username;

    public RefreshToken(String refreshToken,  String username) {
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public RefreshToken updateToken(String tokenDto) {
        this.refreshToken = tokenDto;
        return this;
    }

}