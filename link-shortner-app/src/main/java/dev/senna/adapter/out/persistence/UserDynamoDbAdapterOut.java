package dev.senna.adapter.out.persistence;

import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.out.UserRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Optional;

import static dev.senna.adapter.out.persistence.UserEntity.EMAIL_INDEX;

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
}
