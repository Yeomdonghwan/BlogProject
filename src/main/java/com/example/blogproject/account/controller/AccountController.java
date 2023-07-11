package com.example.blogproject.account.controller;

import com.example.blogproject.account.dto.AccountReqDto;
import com.example.blogproject.account.dto.LoginReqDto;
import com.example.blogproject.account.entity.Account;
import com.example.blogproject.account.service.AccountService;
import com.example.blogproject.global.dto.GlobalResDto;
import com.example.blogproject.security.user.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<GlobalResDto> register(@RequestBody @Valid AccountReqDto accountReqDto) {
        GlobalResDto response = accountService.signup(accountReqDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResDto> login(@RequestBody @Valid LoginReqDto loginReqDto, HttpServletResponse response) {
        GlobalResDto loginResponse = accountService.login(loginReqDto, response);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<GlobalResDto> adminOnlyMethod() {
        // ROLE_ADMIN 권한이 있는 사용자만 실행 가능한 로직
        return new ResponseEntity<>(new GlobalResDto("success!!",200),HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<Account> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(userDetails.getAccount()); // 로그인되지 않은 경우에 대한 처리
    }

    @GetMapping("/getAuth")
    public ResponseEntity<String> getAuthorities() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 사용자의 권한 정보 가져오기
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 권한 정보를 원하는 형태로 가공하여 반환



        // 여기서는 간단히 문자열 리스트로 반환하도록 예시로 작성했습니다.
        return ResponseEntity.ok(authorities.toString());
    }
}
