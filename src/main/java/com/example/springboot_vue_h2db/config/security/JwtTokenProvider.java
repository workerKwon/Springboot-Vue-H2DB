package com.example.springboot_vue_h2db.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

// Jwt 토큰 생성 및 유효성 검증을 하는 컴포넌트
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private long tokenValidMillisecond = 1000L * 60 * 60;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        // Claim 정보에는 토큰에 부가적으로 실어보낼 정보를 세팅할 수 있다.
        // claim 정보에 회원을 구분할 수 있는 값을 세팅하고 토큰이 들어오면 해당 값으로 회원을 구분하여 리소스를 제공하면 됨.
        Claims claims = Jwts.claims().setSubject(userPk); // claims의 subject에 msrl을 세팅하고 role을 Map으로 세팅한다.
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 만료시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Jwt 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        // msrl(pk id)로 user 객체를 가져온다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token)); // msrl로 유저를 불러온다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); // 불러온 유저를 인증 정보 객체로 만든다.
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); // Jwt에 (로그인하는 key)secretKey로 token을 parsing하여 subject(msrl)를 가져온다.
    }

    //Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt 토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
