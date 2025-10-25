package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.LoginRequest;
import dev.senna.adapter.in.web.dto.response.LoginResponse;
import dev.senna.core.usecase.AuthenticationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class TokenControllerAdapterIn {

    private final AuthenticationUseCase authenticationUseCase;

    public TokenControllerAdapterIn(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var resp = authenticationUseCase.execute(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping()
    public ResponseEntity<String> isAuthenticated() {
        var resp = "Is authenticated";
        return ResponseEntity.ok().body(resp);
    }
}
