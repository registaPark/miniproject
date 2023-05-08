package com.hanghae99.dog.user.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Dto.StatusResponseDto;
import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }
    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return userService.logout(userDetails.getUser(), response);
    }
}
