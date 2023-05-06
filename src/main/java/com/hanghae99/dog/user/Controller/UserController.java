package com.hanghae99.dog.user.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Dto.StatusResponseDto;
import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


//    @GetMapping("/api/signup")
//    public ModelAndView signupPage() {
//        return new ModelAndView("signup");
//    }
//
//    @GetMapping("/api/login-page")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
//    }


    @GetMapping("/api/login")
    public StatusResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        return userService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
//        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return new StatusResponseDto(200, "로그인 성공.");
    }

    @PostMapping("/api/admin/signup")
    public StatusResponseDto adminSignup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signUp(userRequestDto);
    }
    @PostMapping("/api/admin/login")
    public StatusResponseDto adminLogin(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @PostMapping("/api/logout")
    public StatusResponseDto logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        return userService.logout(userDetails.getUser(), response);
    }
}
