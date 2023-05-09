package com.hanghae99.dog.consulting.repository;

import com.hanghae99.dog.consulting.entity.Consulting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class ConsultingRepositoryTest {

    @Autowired
    private ConsultingRepository consultingRepository;

    @Test
    @DisplayName("상담 신청 저장 테스트")
    public void whenApplyConsulting_thenReturnConsulting() {
        //given
        Consulting consulting = new Consulting();
        consulting.setUsername("홍길동");
        consulting.setDays("2023-05-10");
        consulting.setTime("15:00");
        consulting.setPhoneNumber("010-1234-5678");

        consultingRepository.save(consulting);
        consultingRepository.flush();

        //when
        List<Consulting> findAllConsulting = consultingRepository.findAll();

        //then
        assertThat(findAllConsulting.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("상담 신청 id로 조회 테스트")
    public void whenFindById_thenReturnConsulting() {
        //given
        Consulting consulting = new Consulting();
        consulting.setUsername("홍길동");
        consulting.setDays("2023-05-10");
        consulting.setTime("15:00");
        consulting.setPhoneNumber("010-1234-5678");

        consultingRepository.save(consulting);
        consultingRepository.flush();

        //when
        Optional<Consulting> findConsultingById = consultingRepository.findById(consulting.getId());

        //then
        assertThat(findConsultingById.isPresent()).isEqualTo(true);
        assertThat(findConsultingById.get().getId()).isEqualTo(consulting.getId());
    }

    @Test
    @DisplayName("상담 신청 id로 조회 실패 테스트")
    public void whenFindById_thenFail() {
        //given
        Consulting consulting = new Consulting();
        consulting.setUsername("홍길동");
        consulting.setDays("2023-05-10");
        consulting.setTime("15:00");
        consulting.setPhoneNumber("010-1234-5678");

        consultingRepository.save(consulting);
        consultingRepository.flush();

        //when
        Optional<Consulting> findConsultingById = consultingRepository.findById(2L);

        //then
        assertThat(findConsultingById.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("상담 취소 테스트")
    public void whenCancelConsulting_thenDeleteConsulting() {
        //given
        Consulting consulting = new Consulting();
        consulting.setUsername("홍길동");
        consulting.setDays("2023-05-10");
        consulting.setTime("15:00");
        consulting.setPhoneNumber("010-1234-5678");

        consultingRepository.save(consulting);
        consultingRepository.flush();

        //when
        consultingRepository.deleteById(consulting.getId());
        consultingRepository.flush();

        //then
        List<Consulting> findAllConsulting = consultingRepository.findAll();
        assertThat(findAllConsulting.size()).isEqualTo(0);
    }


}