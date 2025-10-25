package dev.senna.core.port.out;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkAnalytics;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsRepositoryPortOut {

    void updateClickCount(Link link);

    List<LinkAnalytics> listAnalyticsByLinkId(String linkId, LocalDate startDate, LocalDate endDate);
}
