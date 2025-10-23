package dev.senna.core.usecase;

import dev.senna.adapter.in.web.dto.response.AnalyticsDayResponse;
import dev.senna.adapter.in.web.dto.response.AnalyticsResponse;
import dev.senna.core.domain.LinkAnalytics;
import dev.senna.core.exception.BadFilterDateException;
import dev.senna.core.exception.LinkNotAllowedException;
import dev.senna.core.exception.LinkNotFoundException;
import dev.senna.core.port.in.LinkAnalyticsPortIn;
import dev.senna.core.port.out.AnalyticsRepositoryPortOut;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LinkAnalyticsUseCase implements LinkAnalyticsPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    private final AnalyticsRepositoryPortOut analyticsRepositoryPortOut;

    public LinkAnalyticsUseCase(LinkRepositoryPortOut linkRepositoryPortOut, AnalyticsRepositoryPortOut analyticsRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
        this.analyticsRepositoryPortOut = analyticsRepositoryPortOut;
    }

    @Override
    public AnalyticsResponse execute(String userId, String linkId, LocalDate startDate, LocalDate endDate) {

        validateRangeInputs(startDate, endDate);

        var link = linkRepositoryPortOut.findByLinkId(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!link.isUserOwner(UUID.fromString(userId))) {
            throw new LinkNotAllowedException();
        }

        var analytics = analyticsRepositoryPortOut.listAnalyticsByLinkId(linkId, startDate, endDate);

        var totalVisitors = getTotalVisitors(analytics);

        var analyticsPerDay = getAnalyticsPerDay(analytics);

        return new AnalyticsResponse(totalVisitors,
                analyticsPerDay);
    }

    private void validateRangeInputs(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadFilterDateException(Map.of("startDate", "must be before endDate"));
        }
    }

    private List<AnalyticsDayResponse> getAnalyticsPerDay(List<LinkAnalytics> analytics) {
        return analytics.stream()
                .map(AnalyticsResponse::fromDomain)
                .toList();
    }

    private Long getTotalVisitors(List<LinkAnalytics> analytics) {
        return analytics.stream()
                .map(LinkAnalytics::getClicks)
                .reduce(0L, Long::sum);
    }
}
