package com.hanghae99.dog.user.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.dog.global.Exception.CustomException;
import com.hanghae99.dog.global.Exception.ErrorCode;
import com.hanghae99.dog.user.Dto.KakaoUserInfoDto;
import com.hanghae99.dog.user.Dto.StatusResponseDto;
import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import com.hanghae99.dog.user.Jwt.JwtUtil;
import com.hanghae99.dog.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Value("${user.admin.token}")
    private String ADMIN_TOKEN;

    public StatusResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 반환
//        String createToken =  jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole()));

        return new StatusResponseDto(200, "로그인 성공.");
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "71702a1d0d4ebf81d7c466b9c88a0eac");
        body.add("redirect_uri", "http://localhost:8080/api/login");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    // 3. 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            // email: kakao email
            String email = kakaoUserInfo.getEmail();
            kakaoUser = new User(kakaoUserInfo.getNickname(), kakaoId, encodedPassword, email, UserRoleEnum.USER);
            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    public StatusResponseDto signUp(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.INVALID_USER_EXISTENCE);
        } else {
            if (userRequestDto.isAdmin()) {
                if (userRequestDto.getAdminToken() == null) {
                    throw new CustomException(ErrorCode.INVALID_TOKEN);
                } else {
                    if (userRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                        userRepository.save(new User(userRequestDto.getUsername(), userRequestDto.getPassword(), UserRoleEnum.ADMIN));
                    } else {
                        throw new CustomException(ErrorCode.INVALID_TOKEN);
                    }
                }
            } else {
                throw new CustomException(ErrorCode.ONLY_ADMIN);
            }
        }return new StatusResponseDto(200, "회원가입 성공.");
    }

    public StatusResponseDto login(UserRequestDto userRequestDto, HttpServletResponse servletResponse) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!user.getPassword().equals(userRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_USER_PASSWORD);
        }
        servletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new StatusResponseDto(200, "로그인 성공");
    }

    public StatusResponseDto logout(User user, HttpServletResponse servletResponse) {
        servletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createLogoutToken(user.getUsername(), user.getRole()));
        return new StatusResponseDto(200, "로그아웃 성공.");
    }
}