package com.hanghae99.dog.user.service;

import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import com.hanghae99.dog.user.Repository.UserRepository;
import com.hanghae99.dog.user.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserRequestDto userRequestDto;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("signup test")
    public void signup() {
        // given
        String username = "jjhoon06";
        String password = "q1w2e3r4!!";
        boolean admin = false;
        String adminToken = "";

        // when
        userRequestDto = new UserRequestDto(username, password, admin, adminToken);
        User user = new User(username, password, UserRoleEnum.USER);
//        when(userRepository.save(user)).thenReturn(user);
//        when(userRequestDto.getUsername()).thenReturn(username);
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // then
        assertThat(userService.signup(userRequestDto).getBody()).isEqualTo("회원가입 성공.");
    }

    public void login() {

    }

    public void logout() {

    }
}
