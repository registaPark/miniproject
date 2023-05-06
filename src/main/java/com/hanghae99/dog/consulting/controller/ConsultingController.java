package com.hanghae99.dog.consulting.controller;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.entity.Consulting;
import com.hanghae99.dog.consulting.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ConsultingController {
    private final ConsultingService consultingService;


    @PostMapping("/api/post/consulting")
    public ConsultingResponseDto applyConsulting(@RequestBody ConsultingRequestsDto dto) {
        return consultingService.applyConsulting(dto);
    }

    @DeleteMapping("/api/post/consulting/{id}")
    public CancelConsultingResponseDto cancelConsulting(@PathVariable Long id) {
        return consultingService.cancelConsulting(id);
    }
}
