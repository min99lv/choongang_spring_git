package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig { // 패스워드를 암호화.
	
	@Bean
	 public BCryptPasswordEncoder encodePwd() {
		 return new BCryptPasswordEncoder();
	 }
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest() 	// 어떤 요청
			.permitAll() 	// 모든것을 허용
		;
		
		return http.build(); // http가 어떤 요구라도 허용을 해주겠다.
	}
}
