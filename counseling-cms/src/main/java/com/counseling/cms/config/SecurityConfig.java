package com.counseling.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@ EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		 http
         .authorizeHttpRequests(authorize -> authorize
        		.requestMatchers("/**").permitAll() // 모든 사용자가 접근 가능
        	 //.requestMatchers("/static/**","/","/admin/login","/user/**","/board/**").permitAll() // 모든 사용자가 접근 가능
        	 //.requestMatchers("/counselor/**").hasRole("C") 	// 교수와 상담사만 접근 가능
        	 //.requestMatchers("/admin/**").hasRole("A") // ADMIN 역할만 접근 가능
        	 //.requestMatchers("/admin/admin-list").hasRole("M") // MASTER 역할만 접근 가능
             //.anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
         )
         /*
         .formLogin(form -> form
             .loginPage("/login")
             .permitAll()
             .successHandler() // 로그인 성공 후 처리
             .failureHandler() // 로그인 실패 후 처리
         )
         .logout(logout -> logout	
             .permitAll()
             .logoutSuccessUrl("/login?logout") // 로그아웃 성공 후 리디렉션 URL
         )
         */
         .csrf(csrf -> csrf
        	        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키로 전송
          );

		
		return http.build();
	}
	
}