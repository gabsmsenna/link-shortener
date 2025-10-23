package dev.senna.core.port.in;

import dev.senna.adapter.in.web.dto.response.AnalyticsResponse;

import java.time.LocalDate;

public interface LinkAnalyticsPortIn {

    AnalyticsResponse execute(String userId, String linkId, LocalDate startDate, LocalDate endDate);
}
