package com.mannavoca.zenga.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mannavoca.zenga.common.logging.LoggingFilter;
import com.mannavoca.zenga.common.security.jwt.JwtAuthenticationEntryPoint;
import com.mannavoca.zenga.common.security.jwt.JwtExceptionFilter;
import com.mannavoca.zenga.common.security.jwt.JwtAuthenticationFilter;
import com.mannavoca.zenga.common.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 기본 웹보안 사용
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final JwtExceptionFilter jwtExceptionFilter;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final LoggingFilter loggingFilter;

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**");
    }


    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .formLogin().disable()
                .httpBasic().disable();

        return httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() // http servletRequest 를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/profile").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

                .and()
                .csrf().disable()// CSRF 설정 Disable
                .cors()

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
//                .addFilterBefore(loggingFilter, JwtExceptionFilter.class)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper))

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(new LoggingFilter(), JwtExceptionFilter.class)

                .build();
    }
}