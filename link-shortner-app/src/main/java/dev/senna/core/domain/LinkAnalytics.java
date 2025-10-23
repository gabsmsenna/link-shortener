package dev.senna.core.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LinkAnalytics {

    private String linkId;

    private LocalDate date;

    private Long clicks;

    private LocalDateTime updatedAt;

    public LinkAnalytics(String linkId, LocalDate date, Long clicks, LocalDateTime updatedAt) {
        this.linkId = linkId;
        this.date = date;
        this.clicks = clicks;
        this.updatedAt = updatedAt;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
