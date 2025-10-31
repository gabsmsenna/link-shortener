package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.CreateUserRequest;
import dev.senna.adapter.in.web.dto.response.CreateUserResponse;
import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.in.BootstrapAdminPortIn;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/bootstrap-admin")
@Validated
public class AdminControllerAdapterIn {

    private final BootstrapAdminPortIn bootstrapAdminPortIn;

    public AdminControllerAdapterIn(BootstrapAdminPortIn bootstrapAdminPortIn) {
        this.bootstrapAdminPortIn = bootstrapAdminPortIn;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> bootstrapAdmin(@RequestBody @Valid CreateUserRequest dto) {

        var userCreated = bootstrapAdminPortIn.execute(dto.toDomain());

        var body = CreateUserResponse.fromDomain(userCreated);

        return ResponseEntity.created(URI.create("/")).body(body);
    }
}
