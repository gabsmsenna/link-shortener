package dev.senna.adapter.in.web.dto.response;

import dev.senna.core.domain.UserDomain;

import java.time.LocalDateTime;

public record CreateUserResponse(String userId,
                                 LocalDateTime createdAt) {

    public static CreateUserResponse fromDomain(UserDomain user) {
        return new CreateUserResponse(user.getUserId().toString(), user.getCreatedAt());
    }
}
