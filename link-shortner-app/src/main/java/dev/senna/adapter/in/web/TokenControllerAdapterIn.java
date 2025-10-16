package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.LoginRequest;
import dev.senna.adapter.in.web.dto.response.LoginResponse;
import dev.senna.core.usecase.AuthenticationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenControllerAdapterIn {

    private final AuthenticationUseCase authenticationUseCase;

    public TokenControllerAdapterIn(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var resp = authenticationUseCase.execute(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<String> isAuthenticated() {
        var resp = "Is authenticated";
        return ResponseEntity.ok().body(resp);
    }
}
