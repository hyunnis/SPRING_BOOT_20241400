
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/hello", "/detailed_web.html",
                                 "/login", "/signup", "/error").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                //.requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/admin/**","/api/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/api/hello").permitAll()
                .anyRequest().authenticated()) // 나머지는 로그인 필요(/mypage 등)

            // 2. 폼 로그인 설정(인증)
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll())

            //3. 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃처리 URL
                .logoutSuccessUrl("/login?logout") // 로그아웃 후 이동
                .invalidateHttpSession(true) // 세션 삭제
                .deleteCookies("JSESSIONID", "remember-me")) // 쿠키 삭제

            //4. [5주차] 로그인 상태 유지
            .rememberMe(remember -> remember
                .key("portfolio-remember-me-key") // 쿠키 서명용 비밀 키
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 7일 유지
            )

            //5.[7주차] REST API 설정
            .httpBasic(Customizer.withDefaults()) // Postman : 요청마다 아이디·비밀번호 (Basic 인증)

            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // JSON API 는 CSRF 토큰 검사 제외

            .exceptionHandling(ex -> ex.defaultAuthenticationEntryPointFor( // 로그인 안 한 API 요청 :
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), // 로그인 화면(302) 대신 401
                request -> request.getRequestURI().startsWith("/api/")));

        return http.build();
    }
}
