package dev.senna.adapter.out.persistence.entities;

import dev.senna.config.TableName;
import dev.senna.core.domain.UserDomain;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.senna.config.Constants.EMAIL_INDEX;

@DynamoDbBean
@TableName(name = "tb_users")
public class UserEntity {

    private UUID userId;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserEntity() {}

    public static UserEntity fromDomain(UserDomain user) {
        var entity = new UserEntity();

        entity.setUserId(user.getUserId());
        entity.setEmail(user.getEmail());
        entity.setNickname(user.getNickname());
        entity.setPassword(user.getPassword());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;

    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("user_id")
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @DynamoDbAttribute("email")
    @DynamoDbSecondaryPartitionKey(indexNames = EMAIL_INDEX)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDbAttribute("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDbAttribute("nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @DynamoDbAttribute("created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAttribute("updated_at")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserDomain toDomain() {
        return new UserDomain(
                this.userId,
                this.email,
                this.password,
                this.nickname,
                this.createdAt,
                this.updatedAt
        );
    }
}
