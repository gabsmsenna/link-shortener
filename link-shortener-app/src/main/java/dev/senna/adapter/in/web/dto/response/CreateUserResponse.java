package dev.senna.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.senna.core.domain.UserDomain;

import java.time.LocalDateTime;

public record CreateUserResponse(String userId,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
                                 LocalDateTime createdAt) {

    public static CreateUserResponse fromDomain(UserDomain user) {
        return new CreateUserResponse(user.getUserId().toString(), user.getCreatedAt());
    }
}
