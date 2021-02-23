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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일로 회원 로그인")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userRepository.findByUid(id).orElseThrow(CustomEmailSigninFailedException::new);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomEmailSigninFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name) {
        userRepository.save(User.builder()
        .uid(id)
        .password(passwordEncoder.encode(password))
        .name(name)
        .roles(Collections.singletonList("ROLE_USER"))
        .build());
        return responseService.getSuccessResult();
    }
}
