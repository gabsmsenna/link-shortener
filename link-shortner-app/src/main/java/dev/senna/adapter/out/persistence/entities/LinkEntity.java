package dev.senna.adapter.out.persistence.entities;

import dev.senna.config.TableName;
import dev.senna.core.domain.Link;
import dev.senna.core.domain.UserDomain;
import dev.senna.core.domain.UtmTags;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.senna.adapter.out.persistence.DynamoDbAttributeConstants.*;
import static dev.senna.config.Constants.FK_TB_USERS_LINK_USER_INDEX;

@DynamoDbBean
@TableName(name = "tb_user_links")
public class LinkEntity {


    private String linkId;
    private String originalUrl;

    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String utmContent;

    private UUID userId;
    private boolean active;

    private LocalDateTime expirationDateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LinkEntity() {}



    @DynamoDbAttribute(LINK_ID)
    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = FK_TB_USERS_LINK_USER_INDEX)
    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    @DynamoDbAttribute(LINK_ORIGINAL_URL)
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @DynamoDbAttribute(LINK_UTM_SOURCE)
    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    @DynamoDbAttribute(LINK_UTM_MEDIUM)
    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    @DynamoDbAttribute(LINK_UTM_CAMPAIGN)
    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    @DynamoDbAttribute(LINK_UTM_CONTENT)
    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    @DynamoDbAttribute(LINK_USER_ID)
    @DynamoDbSecondaryPartitionKey(indexNames = FK_TB_USERS_LINK_USER_INDEX)
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @DynamoDbAttribute(LINK_ACTIVE)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @DynamoDbAttribute(LINK_EXPIRATION_DATE_TIME)
    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    @DynamoDbAttribute(LINK_CREATED_AT)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAttribute(LINK_UPDATED_AT)
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static LinkEntity fromDomain(Link link) {
        var entity = new LinkEntity();

        entity.setLinkId(link.getLinkId());
        entity.setOriginalUrl(link.getOriginalUrl());

        if (link.getUtmTags() != null) {
            entity.setUtmSource(link.getUtmTags().getUtmSource());
            entity.setUtmMedium(link.getUtmTags().getUtmMedium());
            entity.setUtmCampaign(link.getUtmTags().getUtmCampaign());
            entity.setUtmContent(link.getUtmTags().getUtmContent());
        }

        entity.setUserId(link.getUser().getUserId());
        entity.setActive(link.isActive());

        entity.setExpirationDateTime(link.getExpirationDateTime());
        entity.setCreatedAt(link.getCreatedAt());
        entity.setUpdatedAt(link.getUpdatedAt());

        return entity;
    }

    public static Link toDomain(LinkEntity entity) {
        return new Link(
                entity.getLinkId(),
                entity.originalUrl,
                new UtmTags(entity.utmSource, entity.utmMedium, entity.utmCampaign, entity.utmContent),
                new UserDomain(entity.userId),
                entity.isActive(),
                entity.getExpirationDateTime(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
