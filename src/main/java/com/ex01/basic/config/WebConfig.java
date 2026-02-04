package com.ex01.basic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //addMapping : 모든 엔드포인트(api/v1/members)(종착지에 대한 경로) 허용
        // 컨트롤러의 모든 경로들(다른 서버에서 접근하고자 할 때 허용해줘야 함)
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //허용할 프론트엔드 url
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*") //모든 헤더 허용
                .allowCredentials(true); //쿠키 전달허용
    }
}
