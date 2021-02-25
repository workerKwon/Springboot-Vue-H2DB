package com.example.springboot_vue_h2db.service.userDetailService;

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

    @Override // 유저 이름으로 유저를 로드하는 추상메소드. 난 userName으로 msrl(pk id)를 넣어줬다.
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // userRepository는 User 타입을 받고 User 객체는 UserDetails를 받아서 구현하기 때문에 리턴 가능.
        return userRepository.findById(Long.valueOf(s)).orElseThrow(() -> new UsernameNotFoundException("User can not found."));
    }
}
