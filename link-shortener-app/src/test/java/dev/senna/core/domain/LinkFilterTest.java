package dev.senna.core.domain;

import dev.senna.core.exception.BadFilterDateException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinkFilterTest {

    @Nested
    class validate {

        @Test
        void shouldNotThrowFilterExceptionWhenStartCreatedAtIsNull() {
            LocalDate startCreatedAt = null;
            LocalDate endCreatedAt = LocalDate.now().plusDays(2);

            var linkFilter = new LinkFilter(false, startCreatedAt, endCreatedAt);
            assertDoesNotThrow(linkFilter::validate);
        }

        @Test
        void shouldNotThrowFilterExceptionWhenEndCreatedAtIsNull() {
            LocalDate startCreatedAt = LocalDate.now().plusDays(2);
            LocalDate endCreatedAt = null;

            var linkFilter = new LinkFilter(false, startCreatedAt, endCreatedAt);
            assertDoesNotThrow(linkFilter::validate);
        }

        @Test
        void shouldNotThrowFilterExceptionWhenBothAreNull() {
            var linkFilter = new LinkFilter(false, null, null);
            assertDoesNotThrow(linkFilter::validate);
        }

        @Test
        void shouldThrowFilterExceptionWhenStartCreatedAtIsAfterEndCreatedAt() {
            LocalDate endCreatedAt = LocalDate.now().plusDays(5);
            LocalDate startCreatedAt = endCreatedAt.plusDays(1);

            var linkFilter = new LinkFilter(false, startCreatedAt, endCreatedAt);
            assertThrows(BadFilterDateException.class, linkFilter::validate);
        }
    }

}