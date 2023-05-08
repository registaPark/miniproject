package com.hanghae99.dog.consulting.controller;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/")
public class ConsultingController {
    private final ConsultingService consultingService;

    @PostMapping("/consulting")
    public ResponseEntity<?> applyConsulting(@RequestBody ConsultingRequestsDto dto) {
        ConsultingResponseDto responseDto = consultingService.applyConsulting(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/consulting/{id}")
    public ResponseEntity<CancelConsultingResponseDto> cancelConsulting(@PathVariable Long id) {
        CancelConsultingResponseDto responseDto = consultingService.cancelConsulting(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
