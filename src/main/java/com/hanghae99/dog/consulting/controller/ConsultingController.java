package com.hanghae99.dog.consulting.controller;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.service.ConsultingService;
import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/")
public class ConsultingController {
    private final ConsultingService consultingService;

    @PostMapping("/consulting")
    public ResponseEntity<?> applyConsulting(@RequestBody ConsultingRequestsDto dto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        ConsultingResponseDto responseDto = consultingService.applyConsulting(dto, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/consulting/{id}")
    public ResponseEntity<CancelConsultingResponseDto> cancelConsulting(@PathVariable Long id, Authentication authentication) {
        CancelConsultingResponseDto responseDto = consultingService.cancelConsulting(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
