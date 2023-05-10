package com.hanghae99.dog.user.Jwt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.dog.global.Exception.ErrorCode;
import com.hanghae99.dog.user.Dto.StatusResponseDto;
import com.hanghae99.dog.user.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // JWT 토큰을 해석하여 추출
        String accessToken = jwtUtil.resolveToken(request, JwtUtil.ACCESS_KEY);
        String refreshToken = jwtUtil.resolveToken(request, JwtUtil.REFRESH_KEY);

        String checkRefresh = jwtUtil.checkRefresh(accessToken, refreshToken);
        if (checkRefresh.equals("success")) {
            setAuthentication(jwtUtil.getUserInfoFromToken(accessToken));
        } else if (checkRefresh.equals("access")) {
            String username = jwtUtil.getUserInfoFromToken(accessToken);
            User user = jwtUtil.checkGetUserByUsername(username);
            String newRefreshToken = jwtUtil.createToken(username, user.getRole(), "Refresh");
            response.setHeader(JwtUtil.REFRESH_KEY, newRefreshToken);
            setAuthentication(username);
        } else if (checkRefresh.equals("refresh")) {
            String username = jwtUtil.getUserInfoFromToken(refreshToken);
            User user = jwtUtil.checkGetUserByUsername(username);
            String newAccessToken = jwtUtil.createToken(username, user.getRole(), "Access");
            response.setHeader(JwtUtil.ACCESS_KEY, newAccessToken);
            setAuthentication(username);
        } else if (checkRefresh.equals("fail")) {
            filterChain.doFilter(request, response);
        } else {
            jwtExceptionHandler(response, ErrorCode.INVALID_TOKEN.getMessage(), ErrorCode.INVALID_TOKEN.getHttpStatus().value());
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            log.error(msg);
            String json = new ObjectMapper().writeValueAsString(new StatusResponseDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

