package dev.senna.adapter.in.web.dto;

import dev.senna.core.domain.UserDomain;

public record CreateUserReq(String email,
                            String password,
                            String nickname) {

    public UserDomain toDomain() {
        return new UserDomain(email, password, nickname);
    }
}
