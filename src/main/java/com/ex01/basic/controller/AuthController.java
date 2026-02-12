package com.ex01.basic.controller;

import com.ex01.basic.config.JwtUtil;
import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginDto loginDto){
        //로그인 인증(토큰)
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                );
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            throw new InvalidPasswordException("비밀번호가 일치하지 않는다");
        }

        //try {
        //로그인 인증 유효성 검사

        System.out.println("인증된 사용자 정보 : "+authentication.getPrincipal());
        //로그인(인증) 성공시 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        System.out.println("userDetails.getAuthorities : "+userDetails.getAuthorities());
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String resultToken = jwtUtil.generateToken(userDetails.getUsername(), role);
        //인증 성공이라면 토큰 발급
        //System.out.println("name : "+authentication.getName());
        //System.out.println("name : "+userDetails.getUsername());
        //System.out.println("auth : "+userDetails.getAuthorities());
        // } catch (Exception e) {
        //     System.out.println("에러 : "+e);
        //}
        //Map<String, String> map = new HashMap<>();
        //map.put("token", resultToken);
        //return ResponseEntity.ok(map):
        return ResponseEntity.ok(Collections.singletonMap("token", resultToken));
    }
}
