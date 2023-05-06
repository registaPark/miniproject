package com.hanghae99.dog.init;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.dog.animal.entity.Animal;
import com.hanghae99.dog.image.entity.Image;
import com.hanghae99.dog.animal.repository.AnimalRepository;
import com.hanghae99.dog.image.repository.ImageRepository;
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
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ApiDataInit {
    private final AnimalRepository animalRepository;
    private final ImageRepository imageRepository;
    private final HashMap<Long,Animal> animalHashMap = new HashMap<>(); //이미지를 넣어주기 위해 해시맵으로 animal들을 가지고 있음.
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
                animalHashMap.put(animalId,animal); // Aniaml 리스트에 추가
                animalRepository.save(animal); // Animal 저장
            }
            conn.disconnect(); //커넥션 리소스 해제
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            imageUrlSetting(); // 이미지 url DB에 넣기 위해 finally에서 실행
        }
    }

    @Transactional
    public void imageUrlSetting() {
            URL getPhotoUrl = null;
            try {
                getPhotoUrl = new URL("http://openapi.seoul.go.kr:8088/"+secretKey+"/json/TbAdpWaitAnimalPhotoView/1/1000/?STAGE_SN=2&fileNm="); // 이미지 url 받아오기 위한 공공api
                HttpURLConnection conn2 =(HttpURLConnection) getPhotoUrl.openConnection();
                conn2.setRequestMethod("GET");
                conn2.setRequestProperty("Accept","application/json");
                if(conn2.getResponseCode() != 200){
                    throw new RuntimeException("이미지를 불러올수 없습니다.");
                }
                BufferedReader imageBr = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(imageBr);
                JsonNode rows = jsonNode.path("TbAdpWaitAnimalPhotoView").path("row");
                for (JsonNode row : rows) { // image엔티티로 매핑해주기 위한 작업
                    String imageUrl = row.path("PHOTO_URL").asText();
                    Long animalNo = row.path("ANIMAL_NO").asLong();
                    Image image = Image.builder().animal(animalHashMap.get(animalNo)).url(imageUrl).build(); // 이미지 엔티티 생성
                    imageRepository.save(image); // 이미지 저장
                }
                conn2.disconnect(); // 커넥션 리소스 해제
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }



}
