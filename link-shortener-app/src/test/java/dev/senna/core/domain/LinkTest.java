package dev.senna.core.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Nested
    class generateFullUrl {

        @Test
        void shouldGenerateFullUrlWithoutUtm() {
            String originalUrl = "https://www.google.com";

            UtmTags utmTags = new UtmTags(
                    null,
                    null,
                    null,
                    null
            );

            Link link = new Link(
                    UUID.randomUUID().toString(),
                    originalUrl,
                    utmTags,
                    null,
                    false,
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                    );

            var fullUrl = link.generateFullUrl();

            assertEquals(originalUrl, fullUrl);
        }

        @Test
        void shouldGenerateFullUrlWhenThereIsUtm() {
            String originalUrl = "https://www.google.com";

            UtmTags utmTags = new UtmTags(
                    "UTM_SOURCE",
                    "UTM_MEDIUM",
                    "UTM_CAMPAIGN",
                    "UTM_CONTENT"
            );

            Link link = new Link(
                    UUID.randomUUID().toString(),
                    originalUrl,
                    utmTags,
                    null,
                    false,
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            var fullurl = link.generateFullUrl();
            var expectedFullUrl = String.format("%s?utm_campaign=UTM_CAMPAIGN&utm_content=UTM_CONTENT&utm_medium=UTM_MEDIUM&utm_source=UTM_SOURCE" ,originalUrl);

            assertEquals(expectedFullUrl, fullurl);
        }
    }
}