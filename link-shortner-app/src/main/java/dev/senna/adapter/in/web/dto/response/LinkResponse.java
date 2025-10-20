package dev.senna.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.senna.core.domain.Link;

import java.time.LocalDateTime;

public record LinkResponse(
        String linkId,
        String originalUrl,
        boolean active,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
        LocalDateTime updatedAt
) {

    public static LinkResponse fromDomain(Link link) {
        return new LinkResponse(
                link.getLinkId(),
                link.getOriginalUrl(),
                link.isActive(),
                link.getCreatedAt(),
                link.getUpdatedAt()
        );
    }
}
