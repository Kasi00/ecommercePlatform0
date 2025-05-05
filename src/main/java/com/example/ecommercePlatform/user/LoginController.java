package com.example.ecommercePlatform.user;

import com.example.ecommercePlatform.user.auth.LoginRequest;
import com.example.ecommercePlatform.user.auth.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }


}
