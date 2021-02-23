package com.example.springboot_vue_h2db.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// Jwt가 유효한 토큰인지 인증하기 위한 Filter.
// security 설정 시 UsernamePasswordAuthenticationFilter 앞에 세팅
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provider 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request); // request의 Header에 X-AUTH-TOKEN의 값을 가져온다.
        if(token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token); // 가져온 token으로 인증정보를 가져와서 저장한다.
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder에 인증정보를 세팅한다.
        }
        filterChain.doFilter(request, response);
    }
}
