package dev.senna.adapter.in.web.dto.response;

import dev.senna.core.domain.LinkAnalytics;

import java.util.List;

public record AnalyticsResponse(Long totalVisitors,
                                List<AnalyticsDayResponse> analyticsDay) {

    public static AnalyticsDayResponse fromDomain(LinkAnalytics linkAnalytics) {
        return new AnalyticsDayResponse(linkAnalytics.getDate(), linkAnalytics.getClicks());
    }
}
