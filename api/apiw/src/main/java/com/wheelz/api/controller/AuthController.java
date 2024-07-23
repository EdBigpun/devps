package com.wheelz.api.controller;

import com.wheelz.api.dto.auth.AuthResponse;
import com.wheelz.api.dto.auth.LoginRequest;
import com.wheelz.api.dto.auth.RegisterRequest;
import com.wheelz.api.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {


    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}

