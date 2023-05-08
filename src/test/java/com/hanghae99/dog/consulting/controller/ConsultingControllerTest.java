//package com.hanghae99.dog.consulting.controller;
//
//import com.hanghae99.dog.consulting.controller.ConsultingController;
//import com.hanghae99.dog.consulting.dto.CancelConsultingResponseDto;
//import com.hanghae99.dog.consulting.dto.ConsultingRequestsDto;
//import com.hanghae99.dog.consulting.dto.ConsultingResponseDto;
//import com.hanghae99.dog.consulting.entity.Consulting;
//import com.hanghae99.dog.consulting.service.ConsultingService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = ConsultingController.class)
//class ConsultingControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ConsultingService consultingService;
//
//
//    @Test
//    @DisplayName("상담 신청 컨트롤러 테스트")
//    public void whenApplyConsulting_thenReturnConsulting() throws Exception {
//        ConsultingRequestsDto dto = new ConsultingRequestsDto();
//        dto.setUsername("홍길동");
//        dto.setDays("2023-05-10");
//        dto.setTime("15:00");
//        dto.setPhoneNumber("010-1234-5678");
//
//        Consulting consulting = new Consulting(dto);
//
//        ConsultingResponseDto responseDto = new ConsultingResponseDto(consulting);
//
//        when(consultingService.applyConsulting(dto)).thenReturn(responseDto);
//
//        mockMvc.perform(post("/api/post/consulting")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"홍길동\",\"days\":\"2023-05-10\",\"time\":\"15:00\",\"phoneNumber\":\"010-1234-5678\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.days").value("2023-05-10"))
//                .andExpect(jsonPath("$.time").value("15:00"));
//    }
//
//    @Test
//    @DisplayName("상담 취소 컨트롤러 테스트")
//    public void whenCancelConsulting_thenReturnCancelConsultingResponse() throws Exception {
//        Long id = 1L;
//
//        CancelConsultingResponseDto responseDto = new CancelConsultingResponseDto("취소 성공", 200);
//
//        when(consultingService.cancelConsulting(id)).thenReturn(responseDto);
//
//        mockMvc.perform(delete("/api/post/consulting/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.msg").value("취소 성공"))
//                .andExpect(jsonPath("$.statusCode").value(200));
//    }
//}
