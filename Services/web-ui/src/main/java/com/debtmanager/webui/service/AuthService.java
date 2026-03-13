package com.debtmanager.webui.service;

import com.debtmanager.webui.client.AuthClient;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthClient authClient;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public String login(String email, String password) { // sin cambios
        return authClient.login(email, password);
    }

    public void register(String fullName, String username, String email, String password) {
        // fullName y username ya no se usan — auth-service solo necesita email y
        // password
        authClient.register(email, password); // CAMBIO — antes llamaba a user-service con 4 parámetros
    }
}
