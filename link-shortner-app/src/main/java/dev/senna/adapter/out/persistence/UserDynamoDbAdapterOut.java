package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.UserEntity;
import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.out.UserRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import static dev.senna.config.Constants.EMAIL_INDEX;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserDynamoDbAdapterOut implements UserRepositoryPortOut {

    private final DynamoDbTemplate dynamoDbTemplate;

    public UserDynamoDbAdapterOut(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public UserDomain save(UserDomain user) {

        var entity = UserEntity.fromDomain(user);
        dynamoDbTemplate.save(entity);
        return user;
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {

        var conditional = QueryConditional.keyEqualTo(k ->
                k.partitionValue(AttributeValue.builder().s(email).build())
        );

        var query = QueryEnhancedRequest.builder()
                .queryConditional(conditional)
                .build();

        var result = dynamoDbTemplate.query(query, UserEntity.class, EMAIL_INDEX);

        return result.stream()
                .flatMap(userEntityPage -> userEntityPage.items().stream())
                .map(UserEntity::toDomain)
                .findFirst();
    }

    @Override
    public void deleteById(UUID userId) {

        var key = Key.builder()
                        .partitionValue(userId.toString())
                        .build();

        dynamoDbTemplate.delete(key, UserEntity.class);
    }

    @Override
    public Optional<UserDomain> findById(UUID userId) {

        var key = Key.builder()
                .partitionValue(userId.toString())
                .build();

        var user = dynamoDbTemplate.load(key, UserEntity.class);

        return user == null ? Optional.empty() : Optional.of(user.toDomain());
    }
}
