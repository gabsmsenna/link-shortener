package dev.senna.adapter.in.web.dto.request;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.UserDomain;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShortenLinkRequest(
       @NotBlank String uniqueLinkSlug,
       @NotBlank String originalUrl,
       UtmTagsRequest utmTags,
       LocalDateTime expirationDateTime

) {

    public Link toDomain(UUID userId) {
        return new Link(
                uniqueLinkSlug,
                originalUrl,
                utmTags.toDomain(),
                new UserDomain(userId),
                true,
                expirationDateTime,
                LocalDateTime.now(),
                LocalDateTime.now()

        );
    }
}
