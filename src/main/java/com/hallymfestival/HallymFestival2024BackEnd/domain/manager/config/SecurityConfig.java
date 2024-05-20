package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.config;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.config.JwtSecurityConfig;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.jwt.JwtAccessDeniedHandler;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.jwt.JwtAuthenticationEntryPoint;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //.antMatchers("/api/admin/community/create/","/api/admin/community/{id}", "/api/admin/notice/create/","/api/admin/notice/{id}", "/api/admin/find/create/", "/api/admin/find/{id}").hasRole("ADMIN")
                .antMatchers("/api/env", "/api/hc").permitAll()
                .antMatchers("/api/admin/login").permitAll()
                .antMatchers("https://hallym-festival-admin.com").permitAll()
                .antMatchers("/**").permitAll()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //Preflight Request 허용해줬는데도 안됨

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors() // CORS 설정 활성화
                .and()
            .authorizeRequests()
                .antMatchers("https://hallym-festival-admin.com").permitAll()
                .and()
            .formLogin()
                .and()
            .logout();
    }
}


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    private final CorsConfig corsConfig;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
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
//                .antMatchers("/api/admin/community", "/api/admin/notice", "/api/admin/find").hasRole("ADMIN")
//                //위 주소는 관리자만 접근
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
//                .antMatchers("http://hallym-festival-admin.com").permitAll()
//                //위 api는 인증 없이 접근 허용
//
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/**")
//                .ignoringAntMatchers("/api/admin/login")
//                .ignoringAntMatchers("/api/admin/sign_up")
//                //csrf 무시
//
//                .and()
//                .addFilter(corsConfig.corsFilter())
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);
//        return httpSecurity.build();
//    }
//}