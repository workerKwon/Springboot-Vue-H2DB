package com.example.springboot_vue_h2db.controller.user;

import com.example.springboot_vue_h2db.advice.exception.CustomEmailSigninFailedException;
import com.example.springboot_vue_h2db.config.security.JwtTokenProvider;
import com.example.springboot_vue_h2db.model.User;
import com.example.springboot_vue_h2db.model.response.CommonResult;
import com.example.springboot_vue_h2db.model.response.SingleResult;
import com.example.springboot_vue_h2db.repo.UserRepository;
import com.example.springboot_vue_h2db.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/_api")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일로 회원 로그인")
    @PostMapping(value = "/signIn")
    public SingleResult<String> signin(@ApiParam(value = "이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        // request의 id(email)로 계정을 찾는다.
        User user = userRepository.findByUid(email).orElseThrow(CustomEmailSigninFailedException::new);
        // request의 password를 인코딩해서 찾아온 패스워드와 비교하고 다르면 exception을 날린다.
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomEmailSigninFailedException();
        }
        // exception이 날아가지 않으면 jwt user의 msrl과 role로 토큰을 만든다.
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signUp")
    public CommonResult signup(@ApiParam(value = "이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "권한", required = true) @RequestParam List<String> roles) {
        userRepository.save(User.builder()
        .uid(email)
        .password(passwordEncoder.encode(password))
        .name(name)
        .roles(roles)
        .build());
        return responseService.getSuccessResult();
    }
}
