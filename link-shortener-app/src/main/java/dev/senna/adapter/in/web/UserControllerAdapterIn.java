package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.CreateUserRequest;
import dev.senna.adapter.in.web.dto.response.CreateUserResponse;
import dev.senna.config.FeatureFlagConfig;
import dev.senna.core.exception.ResourceNotAvailableException;
import dev.senna.core.port.in.CreateUserPortIn;
import dev.senna.core.port.in.DeleteUserPortIn;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;


@RestController()
@RequestMapping("/api/users")
public class UserControllerAdapterIn {

    private final CreateUserPortIn createUserPortIn;
    private final DeleteUserPortIn deleteUserPortIn;
    private final FeatureFlagConfig featureFlagConfig;

    public UserControllerAdapterIn(CreateUserPortIn createUserPortIn, DeleteUserPortIn deleteUserPortIn, FeatureFlagConfig featureFlagConfig) {
        this.createUserPortIn = createUserPortIn;
        this.deleteUserPortIn = deleteUserPortIn;
        this.featureFlagConfig = featureFlagConfig;
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest req) {

        if (!featureFlagConfig.getCreateUsersEnabled()) {
            throw new ResourceNotAvailableException();
        }

        var userCreated = createUserPortIn.execute(req.toDomain());

        var body = CreateUserResponse.fromDomain(userCreated);

        return ResponseEntity.created(URI.create("/")).body(body);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(JwtAuthenticationToken token) {

        var userId = String.valueOf(token.getTokenAttributes().get("sub"));

        deleteUserPortIn.execute(UUID.fromString(userId));

        return ResponseEntity.noContent().build();
    }
}
