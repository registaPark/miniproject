package com.hanghae99.dog.global.Config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(final CorsRegistry registry){
        registry.addMapping("/**")
                .exposedHeaders("ACCESS_KEY", "REFRESH_KEY")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // 허용할 HTTP method
    }
}
