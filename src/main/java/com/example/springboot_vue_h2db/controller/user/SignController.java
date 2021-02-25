package com.example.springboot_vue_h2db.controller.user;

import com.example.springboot_vue_h2db.config.security.JwtTokenProvider;
import com.example.springboot_vue_h2db.model.User;
import com.example.springboot_vue_h2db.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/_api")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일로 회원 로그인")
    @PostMapping(value = "/signIn")
    public String signin(@ApiParam(value = "이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                                       HttpServletRequest request, HttpServletResponse response) {
        // request의 id(email)로 계정을 찾는다.
        User user = userRepository.findByUid(email).orElseThrow(() -> new UsernameNotFoundException("User can not found."));
        // request의 password를 인코딩해서 찾아온 패스워드와 비교하고 다르면 exception을 날린다.
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("User can not found.");
        }
        // exception이 날아가지 않으면 jwt user의 msrl과 role로 토큰을 만든다.
        String token = jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles());
        return token;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signUp")
    public void signup(@ApiParam(value = "이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "권한", required = true) @RequestParam List<String> roles) {
        userRepository.save(User.builder()
        .uid(email)
        .password(passwordEncoder.encode(password))
        .name(name)
        .roles(roles)
        .build());
    }
}
