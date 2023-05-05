package com.hanghae99.dog.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Component
@RequiredArgsConstructor
public class DataSource {
    private final EntityManager em;
    @PostConstruct
    public void init(){
        try {
            URL url = new URL("http://http://openapi.seoul.go.kr:8088/6b53454b47616d6934344d7a585076/json/TbAdpWaitAnimalView//1/5");
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            if(conn.getResponseCode() != 200){
                throw new RuntimeException("Api를 불러올수 없습니다.");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            System.out.println(br.readLine());
            conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
