package dev.senna.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDomain {


    private UUID userId;


    private String email;


    private String password;


    private String nickname;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    public UserDomain(String email, String password, String nickname) {
        this.userId = UUID.randomUUID();
        this.email = email;
        this.password = password; // TODO - encode the password
        this.nickname = nickname;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
