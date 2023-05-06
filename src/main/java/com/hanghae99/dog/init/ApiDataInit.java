package com.hanghae99.dog.init;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiDataInit {
    private final AnimalRepository animalRepository;
    private final List<Animal> animalList = new ArrayList<>(); //이미지를 넣어주기 위해 리스트로 animal들을 가지고 있음.
    @Value("${openApi.secret.key}")
    private String secretKey; // 공공Api SecretKey 숨기기 위해 Value로 받아옴
    @PostConstruct
    @Transactional
    public void init(){
        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/"+secretKey+"/json/TbAdpWaitAnimalView/1/100"); //유기동물 정보 받아오는 공공 api
            HttpURLConnection conn =(HttpURLConnection) url.openConnection(); // 커넥션 연결
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            if(conn.getResponseCode() != 200){
                throw new RuntimeException("Api를 불러올수 없습니다."); //응답코드 200이 아닐시 예외 발생
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(br);
            JsonNode rows = jsonNode.path("TbAdpWaitAnimalView").path("row");
            for (JsonNode rowNode : rows) { // Json에서 각 데이터들을 Animal엔티티로 매핑해주기 위한 작업
                long animalId = rowNode.path("ANIMAL_NO").asLong();
                String name = rowNode.path("NM").asText();
                String entrncDate = rowNode.path("ENTRNC_DATE").asText();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate entrance_date = LocalDate.parse(entrncDate, dateTimeFormatter);
                String species = rowNode.path("SPCS").asText();
                String breed = rowNode.path("BREEDS").asText();
                String sex = rowNode.path("SEXDSTN").asText();
                String age = rowNode.path("AGE").asText();
                Float weight =Float.parseFloat(rowNode.path("BDWGH").asText());
                String adpSttus = rowNode.path("ADP_STTUS").asText();
                String introduceUrl = rowNode.path("INTRCN_MVP_URL").asText();
                String tmprPrtcCn = rowNode.path("TMPR_PRTC_CN").asText();
                Animal animal = Animal.builder() //Animal 객체 생성
                        .animalNo(animalId)
                        .name(name)
                        .entrance_date(entrance_date)
                        .species(species)
                        .breed(breed)
                        .sex(sex)
                        .age(age)
                        .weight(weight)
                        .adpStatus(adpSttus)
                        .introduceUrl(introduceUrl)
                        .tmpr(tmprPrtcCn)
                        .build();
                animalList.add(animal); // Aniaml 리스트에 추가
                animalRepository.save(animal); // Animal 저장
            }
            conn.disconnect(); //커넥션 리소스 해제
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
