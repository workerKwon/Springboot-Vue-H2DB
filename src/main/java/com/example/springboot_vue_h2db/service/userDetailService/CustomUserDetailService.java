package com.example.springboot_vue_h2db.service.userDetailService;

import com.example.springboot_vue_h2db.advice.exception.CustomNotFoundException;
import com.example.springboot_vue_h2db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService를 재정의.
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(s)).orElseThrow(CustomNotFoundException::new);
    }
}
