package com.counseling.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.counseling.cms.jwt.JwtRequestFilter;
import com.counseling.cms.jwt.JwtUtil;
import com.counseling.cms.jwt.UserDetailsServiceImpl;

@Configuration
@ EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	 @Autowired
	    private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		 http
         .authorizeHttpRequests(authorize -> authorize
        	 .requestMatchers("/**","/images/**","/css/**","/js/**","/admin/login","/user/**","/board/**").permitAll() // 모든 사용자가 접근 가능
        	 .requestMatchers("/counselor/**").hasRole("C") 	// 교수와 상담사만 접근 가능
        	 .requestMatchers("/admin/**").hasRole("A") // ADMIN 역할만 접근 가능
        	 .requestMatchers("/admin/admin-list").hasRole("M") // MASTER 역할만 접근 가능
             .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
         )
         .formLogin(authorize->authorize.disable()	//form 로그인 방식 비활성화
         )
         .httpBasic(authorize->authorize.disable()		//http basic 인증 방식 비활성화
         )
         .sessionManagement((session) -> session
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)		//session 사용 안함
         )
         .csrf(csrf -> csrf
            	 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키로 전송(나중에 disable)
         );
		 
		 http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
         

         return http.build();
	}
	

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                new AuthenticationManagerBuilder((ObjectPostProcessor<Object>) http.getSharedObject(AuthenticationManagerBuilder.class));
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }
	
} 
