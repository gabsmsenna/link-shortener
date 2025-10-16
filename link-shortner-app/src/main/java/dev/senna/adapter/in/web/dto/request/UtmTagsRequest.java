package dev.senna.adapter.in.web.dto.request;

import dev.senna.core.domain.UtmTags;

public record UtmTagsRequest(String source,
                             String medium,
                             String campaing,
                             String content) {
    public UtmTags toDomain() {
        return new UtmTags(
                this.source,
                this.medium,
                this.campaing,
                this.content
        );
    }
}
