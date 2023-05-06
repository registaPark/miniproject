package com.hanghae99.dog.consulting.service;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.entity.Consulting;
import com.hanghae99.dog.consulting.repository.ConsultingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;



    // 상담 신청 메소드
    @Transactional
    public ConsultingResponseDto applyConsulting(ConsultingRequestsDto dto) {
        Consulting consulting = new Consulting(dto);
        Consulting savedConsulting = consultingRepository.save(consulting);
        return new ConsultingResponseDto(savedConsulting);
    }

    // 상담 취소 메소드
    @Transactional
    public CancelConsultingResponseDto cancelConsulting(Long id) {
        consultingRepository.deleteById(id);
        return new CancelConsultingResponseDto("취소 성공", 200);
    }
}
