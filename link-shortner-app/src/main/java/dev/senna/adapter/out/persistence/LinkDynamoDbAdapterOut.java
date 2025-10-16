package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.LinkEntity;
import dev.senna.core.domain.Link;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

@Component
public class LinkDynamoDbAdapterOut implements LinkRepositoryPortOut {

    private final DynamoDbTemplate dynamoDbTemplate;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(LinkDynamoDbAdapterOut.class);

    public LinkDynamoDbAdapterOut(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public Link save(Link link) {
        var entity = LinkEntity.fromDomain(link);

        logger.info("------------------ Link saved: {}", link);
        logger.info("------------------ Link entity: {}", entity);

        dynamoDbTemplate.save(entity);

        return link;
    }

    @Override
    public Optional<Link> findByLinkId(String linkId) {

        var key = Key.builder()
                .partitionValue(linkId)
                .build();

        var entity = dynamoDbTemplate.load(key, LinkEntity.class);

        return entity == null ?
                Optional.empty() :
                Optional.of(entity.toDomain(entity));
    }
}
