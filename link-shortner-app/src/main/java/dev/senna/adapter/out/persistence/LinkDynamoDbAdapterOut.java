package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.LinkEntity;
import dev.senna.adapter.out.persistence.helper.DynamoTokenHelper;
import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static dev.senna.config.Constants.FK_TB_USERS_LINK_USER_INDEX;
import static java.util.Objects.isNull;

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
    public PaginatedResult<Link> findAllByUserId(String userId,
                                                 String nextToken,
                                                 int limit,
                                                 LinkFilter filters) {

        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder()
                    .partitionValue(userId)
                    .build());


        List<String> conditions = new ArrayList<>();
        Map<String, AttributeValue> expressionValues = new HashMap<>();

        buildFiltersParam(filters, conditions, expressionValues);

        var query = buildQueryEnhancedRequest(nextToken, limit, queryConditional, conditions, expressionValues);

        var page = executeQuery(query);

        return convertAndReturn(page);
    }

    private static void buildFiltersParam(LinkFilter filters, List<String> conditions, Map<String, AttributeValue> expressionValues) {
        if (!isNull(filters.active())) {
            conditions.add("active = :activeValue");
            expressionValues.put(":activeValue", AttributeValue.fromBool(filters.active()));
        }

        if (!isNull(filters.startCreatedAt()) && !isNull(filters.endCreatedAt())) {
            conditions.add("created_at BETWEEN :startCreatedAt AND :endCreatedAt");
            expressionValues.put(":startCreatedAt", AttributeValue.fromS(LocalDateTime.of(filters.startCreatedAt(), LocalTime.MIN).toString()));
            expressionValues.put(":endCreatedAt", AttributeValue.fromS(LocalDateTime.of(filters.endCreatedAt(), LocalTime.MAX).toString()));
        }
    }

    private QueryEnhancedRequest buildQueryEnhancedRequest(String nextToken, int limit, QueryConditional queryConditional, List<String> conditions, Map<String, AttributeValue> expressionValues) {
        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(limit);

        if (!conditions.isEmpty()) {
            requestBuilder.filterExpression(
                    Expression.builder()
                            .expression(String.join(" AND ", conditions))
                            .expressionValues(expressionValues)
                            .build()
            );
        }

        if (nextToken != null && !nextToken.isEmpty()) {
            var map = dynamoTokenHelper.decodeStartToken(nextToken);
            requestBuilder.exclusiveStartKey(map);
        }
        return requestBuilder.build();
    }

    private Page<LinkEntity> executeQuery(QueryEnhancedRequest query) {
        return dynamoDbTemplate
                .query(query, LinkEntity.class, FK_TB_USERS_LINK_USER_INDEX)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private PaginatedResult<Link> convertAndReturn(Page<LinkEntity> page) {
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
