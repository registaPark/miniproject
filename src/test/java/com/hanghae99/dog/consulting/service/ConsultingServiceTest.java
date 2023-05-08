package com.hanghae99.dog.consulting.service;

import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
import com.hanghae99.dog.consulting.entity.Consulting;
import com.hanghae99.dog.consulting.repository.ConsultingRepository;
import com.hanghae99.dog.user.Entity.User;
import com.hanghae99.dog.user.Entity.UserRoleEnum;
import com.hanghae99.dog.user.Repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultingServiceTest {
    @Mock
    private ConsultingRepository consultingRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConsultingService consultingService;

    @Test
    @DisplayName("상담 신청 테스트")
    public void applyConsultingTest() {
        //given
        ConsultingRequestsDto dto = new ConsultingRequestsDto();
        dto.setUsername("홍길동");
        dto.setDays("월요일");
        dto.setTime("오후 3시");
        dto.setPhoneNumber("010-1234-5678");

        Long userId = 1L;
        User user = new User("홍길동", "password123", UserRoleEnum.USER);

        Consulting consulting = new Consulting(dto, user);
        Consulting savedConsulting = new Consulting(dto, user);

        //when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(consultingRepository.save(any(Consulting.class))).thenReturn(savedConsulting);

        //then
        ConsultingResponseDto result = consultingService.applyConsulting(dto, userId);

        // 속성별로 비교
        assertThat(result.getDays()).isEqualTo(savedConsulting.getDays());
        assertThat(result.getTime()).isEqualTo(savedConsulting.getTime());
    }

    @Test
    @DisplayName("상담 취소 테스트")
    public void cancelConsultingTest(){
        //given
        Long id = 1L;

        //when
        CancelConsultingResponseDto result = consultingService.cancelConsulting(id);

        //then
        verify(consultingRepository).deleteById(id);
        assertThat(result.getMsg()).isEqualTo("취소 성공");
        assertThat(result.getStatusCode()).isEqualTo(200);
    }

}