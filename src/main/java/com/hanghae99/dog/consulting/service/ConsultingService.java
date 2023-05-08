package com.hanghae99.dog.consulting.service;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.entity.Consulting;
import com.hanghae99.dog.consulting.repository.ConsultingRepository;
import com.hanghae99.dog.global.Security.UserDetailsImpl;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultingService {
    private final ConsultingRepository consultingRepository;
    private final UserRepository userRepository;




    // 상담 신청 메소드
    @Transactional
    public ConsultingResponseDto applyConsulting(ConsultingRequestsDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));



        Consulting consulting = new Consulting(dto, user);
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
