package com.hanghae99.dog.user.Entity;

import com.hanghae99.dog.consulting.entity.Consulting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulting> consultings = new ArrayList<>();

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{4,10}",
            message = "아이디는 알파벳 소문자, 숫자를 입력하고 4~10자리로 구성해주세요.")
    private String username;
    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~`!@#$%\\\\^&*()-])(?=\\S+$).{8,15}",
            message = "비밀번호는 알파벳 대소문자, 숫자를 입력하고 8~15자리로 구성해주세요.")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
