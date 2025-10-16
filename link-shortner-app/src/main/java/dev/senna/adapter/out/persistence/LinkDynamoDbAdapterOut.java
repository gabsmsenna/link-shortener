package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.LinkEntity;
import dev.senna.core.domain.Link;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

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
}
