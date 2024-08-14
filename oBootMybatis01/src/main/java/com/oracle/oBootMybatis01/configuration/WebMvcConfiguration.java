package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.oBootMybatis01.service.SampleInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		// registry : 저장장치, 인터셉터를 저장하는 객체
		// 누군가 url/ interceptor --> SampleInterceptor() 처리해줌 
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(new SampleInterceptor()).addPathPatterns("/interCeptor");
	}
}
