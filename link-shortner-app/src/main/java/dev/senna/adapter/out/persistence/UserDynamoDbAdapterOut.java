package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.UserEntity;
import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.out.UserRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(UserDynamoDbAdapterOut.class);

    private final DynamoDbTemplate dynamoDbTemplate;

    public UserDynamoDbAdapterOut(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public UserDomain save(UserDomain user) {

        logger.debug("Saving user on DynamoDB: {}", user);

        var entity = UserEntity.fromDomain(user);
        dynamoDbTemplate.save(entity);

        logger.debug("User saved on DynamoDB: {}", user);

        return user;
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {

        logger.debug("Finding user by email on DynamoDB. Email used: {}", email);

        var conditional = QueryConditional.keyEqualTo(k ->
                k.partitionValue(AttributeValue.builder().s(email).build())
        );

        var query = QueryEnhancedRequest.builder()
                .queryConditional(conditional)
                .build();

        var result = dynamoDbTemplate.query(query, UserEntity.class, EMAIL_INDEX);

        var opt =  result.stream()
                .flatMap(userEntityPage -> userEntityPage.items().stream())
                .map(UserEntity::toDomain)
                .findFirst();

        logger.debug("User found: {}", opt);

        return opt;
    }

    @Override
    public void deleteById(UUID userId) {

        logger.debug("Deleting user by id. User id used: {}", userId);

        var key = Key.builder()
                        .partitionValue(userId.toString())
                        .build();

        dynamoDbTemplate.delete(key, UserEntity.class);

        logger.debug("User deleted successfully!");
    }

    @Override
    public Optional<UserDomain> findById(UUID userId) {

        logger.debug("Finding user by id. User id used: {}", userId);

        var key = Key.builder()
                .partitionValue(userId.toString())
                .build();

        var user = dynamoDbTemplate.load(key, UserEntity.class);

        Optional<UserDomain> opt =  user == null ? Optional.empty() : Optional.of(user.toDomain());

        logger.debug("User found: {}", opt);

        return opt;
    }
}
