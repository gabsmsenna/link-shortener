package dev.senna.adapter.in.web.dto.request;

import dev.senna.core.domain.UserDomain;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(@NotBlank @Length(max = 100) String email,
                                @NotBlank @Length(min = 8) String password,
                                @NotBlank @Length(min = 5, max = 50) String nickname) {

    public UserDomain toDomain() {
        return new UserDomain(email, password, nickname);
    }
}
