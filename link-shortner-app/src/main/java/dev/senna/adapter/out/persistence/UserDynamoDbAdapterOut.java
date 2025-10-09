package dev.senna.adapter.out.persistence;

import dev.senna.core.domain.UserDomain;
import dev.senna.core.port.out.UserRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;

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
}
