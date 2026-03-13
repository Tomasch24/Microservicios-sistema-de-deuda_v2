package com.debtmanager.authservice.controller;

import com.debtmanager.authservice.dto.request.LoginRequest;
import com.debtmanager.authservice.dto.request.RegisterRequest; // NUEVO
import com.debtmanager.authservice.dto.response.LoginResponse;
import com.debtmanager.authservice.dto.response.TokenValidationResponse;
import com.debtmanager.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus; // NUEVO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) { // sin cambios
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register") // NUEVO — reemplaza POST /api/v1/users del user-service eliminado
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validate( // sin cambios
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        return ResponseEntity.ok(authService.validateToken(token));
    }
}
