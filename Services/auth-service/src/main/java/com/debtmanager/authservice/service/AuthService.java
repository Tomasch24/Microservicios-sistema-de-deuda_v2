package com.debtmanager.authservice.service;

import com.debtmanager.authservice.dto.request.LoginRequest;
import com.debtmanager.authservice.dto.request.RegisterRequest; // NUEVO — importar el DTO del Paso 1
import com.debtmanager.authservice.dto.response.LoginResponse;
import com.debtmanager.authservice.dto.response.TokenValidationResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request); // sin cambios

    void register(RegisterRequest request); // NUEVO — auth-service maneja el registro, ya no user-service

    TokenValidationResponse validateToken(String token); // sin cambios
}
