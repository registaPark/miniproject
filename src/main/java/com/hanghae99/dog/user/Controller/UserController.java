package com.hanghae99.dog.user.Controller;

import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return userService.logout(userDetails.getUser(), response);
    }
}
