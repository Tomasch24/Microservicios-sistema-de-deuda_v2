package com.debtmanager.authservice.service.impl;

import com.debtmanager.authservice.domain.model.User;
import com.debtmanager.authservice.dto.request.LoginRequest;
import com.debtmanager.authservice.dto.request.RegisterRequest; // NUEVO
import com.debtmanager.authservice.dto.response.LoginResponse;
import com.debtmanager.authservice.dto.response.TokenValidationResponse;
import com.debtmanager.authservice.exception.InvalidCredentialsException;
import com.debtmanager.authservice.repository.UserRepository;
import com.debtmanager.authservice.security.JwtService;
import com.debtmanager.authservice.security.JwtValidator;
import com.debtmanager.authservice.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtValidator jwtValidator;

    public AuthServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            JwtValidator jwtValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtValidator = jwtValidator;
    }

    @Override
    public LoginResponse login(LoginRequest request) { // sin cambios
        User user = userRepository.findByEmailAndEnabledTrue(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciales inválidas.");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole());

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getJwtExpirationMs(),
                user.getRole(),
                user.getEmail());
    }

    @Override
    public void register(RegisterRequest request) { // NUEVO — reemplaza user-service
        // Verificar que el email no esté en uso
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    public TokenValidationResponse validateToken(String token) { // sin cambios
        return jwtValidator.validate(token);
    }
}
