package dev.senna.core.domain;

import dev.senna.core.exception.BadFilterDateException;

import java.time.LocalDate;
import java.util.Map;

import static java.util.Objects.isNull;

public record LinkFilter(
         Boolean active,
         LocalDate startCreatedAt,
         LocalDate endCreatedAt) {

    public void validate() {
        if (!isNull(startCreatedAt) && !isNull(endCreatedAt) && startCreatedAt.isAfter(endCreatedAt)) {
            throw new BadFilterDateException(Map.of("start_created_at", "Must be before end_created-at"));
        }
    }
}
