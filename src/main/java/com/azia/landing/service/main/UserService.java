package com.azia.landing.service.main;

import com.azia.landing.dto.custom.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;


public interface UserService{
    ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request);

    ResponseEntity<?> logout(HttpServletRequest request);
}
