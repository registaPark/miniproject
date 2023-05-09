package com.hanghae99.dog.user.Service;

import com.hanghae99.dog.global.Exception.CustomException;
import com.hanghae99.dog.global.Exception.ErrorCode;
import com.hanghae99.dog.user.Dto.UserRequestDto;
import com.hanghae99.dog.user.Entity.RefreshToken;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import com.hanghae99.dog.user.Jwt.JwtUtil;
import com.hanghae99.dog.user.Jwt.TokenDto;
import com.hanghae99.dog.user.Repository.RefreshTokenRepository;
import com.hanghae99.dog.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${user.admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public ResponseEntity<String> signup(UserRequestDto userRequestDto) {
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
                userRepository.save(new User(userRequestDto.getUsername(), userRequestDto.getPassword(), UserRoleEnum.USER));
            }
        }
        return new ResponseEntity<>("회원가입 성공.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> login(UserRequestDto userRequestDto, HttpServletResponse servletResponse) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!user.getPassword().equals(userRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_USER_PASSWORD);
        }
        TokenDto tokenDto = jwtUtil.createAllToken(user.getUsername(), user.getRole());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(user.getUsername());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user.getUsername());
            refreshTokenRepository.save(newToken);
        }
        servletResponse.addHeader(JwtUtil.ACCESS_KEY, tokenDto.getAccessToken());
        servletResponse.addHeader(JwtUtil.REFRESH_KEY, tokenDto.getRefreshToken());
        return new ResponseEntity<>("로그인 성공.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> logout(User user, HttpServletResponse servletResponse) {
        refreshTokenRepository.deleteByUsername(user.getUsername());
        return new ResponseEntity<>("로그아웃 성공.", HttpStatus.OK);
    }
}