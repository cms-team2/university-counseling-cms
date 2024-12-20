package com.counseling.cms.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.RedirectionEndpointConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@ EnableWebSecurity
public class JwtSecurityConfig {
	 
	 @Autowired
	 private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	 private final JwtRequestFilter jwtRequestFilter;

	 @Autowired
	 private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	 
	 public JwtSecurityConfig(@Lazy JwtRequestFilter jwtRequestFilter) {
	       this.jwtRequestFilter = jwtRequestFilter;
	 }


	 @Bean
	 public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
		 .csrf(csrf -> csrf
        		 .disable() // CSRF 토큰을 쿠키로 전송(나중에 disable)
         )
         .authorizeHttpRequests(authorize -> authorize
    		 .requestMatchers("/user/apply").hasAnyAuthority("A","C","M","N")
    		 .requestMatchers("/chat/**").permitAll()
       		 .requestMatchers("/","/pw/**","/images/**","/css/**","/js/**","/admin/login","/user/**","/board/**").permitAll() // 모든 사용자가 접근 가능
       		 .requestMatchers("/counselor/**").hasAnyAuthority("C","M") 	// 교수와 상담사만 접근 가능
        	 .requestMatchers("/admin/**").hasAnyAuthority("A","M") // ADMIN 역할만 접근 가능
        	 .requestMatchers("/admin/admin-list").hasAuthority("M") // MASTER 역할만 접근 가능     
             .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
         )
         .formLogin(authorize->authorize.disable()	//form 로그인 방식 비활성화
         )
         .httpBasic(authorize->authorize.disable()		//http basic 인증 방식 비활성화
         )
         .sessionManagement((session) -> session
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)		//session 사용 안함
         )
         .exceptionHandling(exception -> exception
                 .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
             )
		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
		);
		 return http.build();
	 }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new CustomUserDetailsService();
	    }

	
} 
