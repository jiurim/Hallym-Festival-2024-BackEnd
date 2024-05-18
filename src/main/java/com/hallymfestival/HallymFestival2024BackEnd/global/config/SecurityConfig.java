package com.hallymfestival.HallymFestival2024BackEnd.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hallymfestival.HallymFestival2024BackEnd.global.jwt.JwtAccessDeniedHandler;
import com.hallymfestival.HallymFestival2024BackEnd.global.jwt.JwtAuthenticationEntryPoint;
import com.hallymfestival.HallymFestival2024BackEnd.global.jwt.JwtAuthenticationFilter;
import com.hallymfestival.HallymFestival2024BackEnd.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/api/hc","/api/env","https://kim-sun-woo.com/api/admin/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/admin/**").permitAll()
                .anyRequest().hasAnyRole("ADMIN")
                .and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }

}

//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                .and()
//                .authorizeRequests()
//                //HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정.
//                //.antMatchers("/api/admin/community", "/api/admin/notice", "/api/admin/find").hasRole("ADMIN")
//                //위 주소는 관리자만 접근
//                .antMatchers("/**").permitAll()
//                .antMatchers("/api/admin/login").permitAll()
//                .antMatchers("api/admin/sign_up").permitAll()
//                .antMatchers("http://13.209.218.51/api/env").permitAll()
//                .antMatchers("http://13.209.218.51/api/hc").permitAll()
//                .antMatchers("http://3.39.62.170/api/env").permitAll()
//                .antMatchers("http://13.209.218.51/api/hc").permitAll()
//                .antMatchers("http://43.202.170.80:8080/api/env").permitAll()
//                .antMatchers("http://43.202.170.80:8080/api/hc").permitAll()
//                .antMatchers("http://43.202.170.80:8081/api/env").permitAll()
//                .antMatchers("http://43.202.170.80:8081/api/hc").permitAll()
//                .antMatchers("http://43.202.160.134:8080/api/env").permitAll()
//                .antMatchers("http://43.202.160.134:8080/api/hc").permitAll()
//                .antMatchers("http://43.202.160.134:8081/api/env").permitAll()
//                .antMatchers("http://43.202.160.134:8081/api/hc").permitAll()
//                .antMatchers("http://3.39.62.170").permitAll()
//                .antMatchers("http://13.209.218.51").permitAll()
//                //위 api는 인증 없이 접근 허용
//
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/**")
//                .ignoringAntMatchers("/api/admin/login")
//                .ignoringAntMatchers("/api/admin/sign_up");
//        //csrf 무시
//    }
//}