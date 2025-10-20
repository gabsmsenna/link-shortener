package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.LinkEntity;
import dev.senna.adapter.out.persistence.helper.DynamoTokenHelper;
import dev.senna.core.domain.Link;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static dev.senna.config.Constants.FK_TB_USERS_LINK_USER_INDEX;

@Component
public class LinkDynamoDbAdapterOut implements LinkRepositoryPortOut {

    private final DynamoDbTemplate dynamoDbTemplate;
    private final DynamoTokenHelper dynamoTokenHelper;

    public LinkDynamoDbAdapterOut(DynamoDbTemplate dynamoDbTemplate, DynamoTokenHelper dynamoTokenHelper) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.dynamoTokenHelper = dynamoTokenHelper;
    }

    @Override
    public Link save(Link link) {
        var entity = LinkEntity.fromDomain(link);

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

    @Override
    public PaginatedResult<Link> finAllByUserId(String userId, String nextToken, int limit ) {

        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(userId)
                .build());

        QueryEnhancedRequest.Builder queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(limit);

        if (nextToken != null && !nextToken.isEmpty()) {
            var map = dynamoTokenHelper.decodeStartToken(nextToken);
            queryEnhancedRequest.exclusiveStartKey(map);
        }

        var page =  dynamoDbTemplate
                .query(queryEnhancedRequest.build(), LinkEntity.class, FK_TB_USERS_LINK_USER_INDEX)
                .stream()
                .findFirst()
                .orElse(null);

        if (page == null) {
            return new PaginatedResult<>(Collections.emptyList(), null, false);
        }

        var links = page.items()
                .stream()
                .map(LinkEntity::toDomain)
                .toList();

        return new PaginatedResult<>(
                links,
                page.lastEvaluatedKey() != null ? dynamoTokenHelper.encodeStartToken(page.lastEvaluatedKey()) : "",
                page.lastEvaluatedKey() != null
        );
    }
}
