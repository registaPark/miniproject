package com.hanghae99.dog.user.Jwt;

import com.hanghae99.dog.global.Exception.CustomException;
import com.hanghae99.dog.global.Exception.ErrorCode;
import com.hanghae99.dog.global.Security.UserDetailsServiceImpl;
import com.hanghae99.dog.user.Entity.RefreshToken;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import com.hanghae99.dog.user.Repository.RefreshTokenRepository;
import com.hanghae99.dog.user.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCESS_KEY = "ACCESS_KEY";
    public static final String REFRESH_KEY = "REFRESH_KEY";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TIME = Duration.ofMinutes(30).toMillis();
    private static final long REFRESH_TIME =Duration.ofDays(14).toMillis();

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String tokenType) {
        String tokenName = tokenType.equals("ACCESS_KEY") ? ACCESS_KEY : REFRESH_KEY;
        String bearerToken = request.getHeader(tokenName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public TokenDto createAllToken(String username, UserRoleEnum role) {
        return new TokenDto(createToken(username, role, "Access"), createToken(username, role, "Refresh"));
    }

    public TokenDto createAllLogoutToken(String username, UserRoleEnum role) {
        return new TokenDto(createLogoutToken(username, role), createLogoutToken(username, role));
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role, String tokenType) {
        Date date = new Date();
        long tokenTime = tokenType.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + tokenTime))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createLogoutToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime()))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public boolean refreshTokenValid(String token) {
        if (!validateToken(token)) return false;
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(getUserInfoFromToken(token));
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().substring(7));
    }

    public String checkRefresh(String accessToken, String refreshToken) {
        if ((accessToken != null && validateToken(accessToken)) && (refreshToken != null && refreshTokenValid(refreshToken)))
            return "success";
        else if ((accessToken != null && validateToken(accessToken)) && (refreshToken == null))
            return "access";
        else if ((accessToken == null) && (refreshToken != null && refreshTokenValid(refreshToken)))
            return "refresh";
//        else if ((accessToken != null && validateToken(accessToken)) && (refreshToken != null && refreshTokenValid(refreshToken)))
//            return "fail";
        else if (accessToken == null && refreshToken == null)
            return "fail";
        else
            return "notExpect";
    }

    // 토큰에서 사용자 정보 가져오기
    public String getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public User checkGetUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                        () -> new CustomException(ErrorCode.INVALID_USER)
                );
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}


