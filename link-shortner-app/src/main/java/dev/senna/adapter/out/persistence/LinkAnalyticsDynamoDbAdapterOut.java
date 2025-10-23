package dev.senna.adapter.out.persistence;

import dev.senna.adapter.out.persistence.entities.LinkAnalyticsEntity;
import dev.senna.adapter.out.persistence.entities.LinkEntity;
import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkAnalytics;
import dev.senna.core.port.out.AnalyticsRepositoryPortOut;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class LinkAnalyticsDynamoDbAdapterOut implements AnalyticsRepositoryPortOut {

    private final DynamoDbTemplate dynamoDbTemplate;
    private final DynamoDbClient dynamoDbClient;

    public LinkAnalyticsDynamoDbAdapterOut(DynamoDbTemplate dynamoDbTemplate, DynamoDbClient dynamoDbClient) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public void updateClickCount(Link link) {

        var date = LocalDate.now();

        var key = Key.builder()
                .partitionValue(link.getLinkId())
                .sortValue(date.toString())
                .build();

        var entity = dynamoDbTemplate.load(key, LinkAnalyticsEntity.class);

        if (entity != null) {

            updateAnalytics(entity, date);

        } else {
            dynamoDbTemplate.save(LinkAnalyticsEntity.fromDomain(link, date));
        }
    }

    @Override
    public List<LinkAnalytics> listAnalyticsByLinkId(String linkId, LocalDate startDate, LocalDate endDate) {

        var query = QueryConditional.sortBetween(
                Key.builder()
                        .partitionValue(linkId)
                        .sortValue(startDate.toString())
                        .build(),
                Key.builder()
                        .partitionValue(linkId)
                        .sortValue(endDate.toString())
                        .build()
        );

        var result = dynamoDbTemplate.query(QueryEnhancedRequest.builder()
                .queryConditional(query)
                .build(), LinkAnalyticsEntity.class);

        return result.items()
                .stream()
                .map(LinkAnalyticsEntity::toDomain)
                .toList();
    }

    private void updateAnalytics(LinkAnalyticsEntity entity,
                                 LocalDate date) {

        Map<String, AttributeValue> key = Map.of(
                "link_id", AttributeValue.fromS(entity.getLinkId()),
                "date", AttributeValue.fromS(date.toString())
        );

        Map<String, AttributeValue> expressionValues = Map.of(
                ":zero", AttributeValue.fromN("0"),
                ":inc", AttributeValue.fromN("1"),
                ":now", AttributeValue.fromS(Instant.now().toString())
        );

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName("tb_links_analytics")
                .key(key)
                .updateExpression("SET clicks = if_not_exists(clicks, :zero) + :inc, updated_at = :now")
                .expressionAttributeValues(expressionValues)
                .build();

        dynamoDbClient.updateItem(request);
    }
}
