package com.azia.landing.controller;

import com.azia.landing.dto.custom.LoginRequest;
import com.azia.landing.service.main.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletRequest request){
        return userService.login(loginRequest, request);
    }


    @PutMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        return userService.logout(request);
    }

}
















