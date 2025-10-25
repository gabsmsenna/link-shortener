package dev.senna.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.senna.config.Constants.*;

public class Link {

    private String linkId;
    private String originalUrl;

    private UtmTags utmTags;

    private UserDomain user;
    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    private LocalDateTime expirationDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    private LocalDateTime updatedAt;

    public Link(String linkId, String originalUrl, UtmTags utmTags, UserDomain user, boolean active, LocalDateTime expirationDateTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.linkId = linkId;
        this.originalUrl = originalUrl;
        this.utmTags = utmTags;
        this.user = user;
        this.active = active;
        this.expirationDateTime = expirationDateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getLinkId() {
        return linkId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public UtmTags getUtmTags() {
        return utmTags;
    }

    public UserDomain getUser() {
        return user;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String generateFullUrl() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(originalUrl);

        if (StringUtils.isNotBlank(utmTags.getUtmCampaign())) {
            builder.queryParam(UTM_CAMPAIGN, utmTags.getUtmCampaign());
        }

        if (StringUtils.isNotBlank(utmTags.getUtmContent())) {
            builder.queryParam(UTM_CONTENT, utmTags.getUtmContent());
        }

        if (StringUtils.isNotBlank(utmTags.getUtmMedium())) {
            builder.queryParam(UTM_MEDIUM, utmTags.getUtmMedium());
        }

        if (StringUtils.isNotBlank(utmTags.getUtmSource())) {
            builder.queryParam(UTM_SOURCE, utmTags.getUtmSource());
        }

        return builder.toUriString();
    }

    public boolean isUserOwner(UUID userId) {
        return this.user.getUserId().equals(userId);
    }
}
