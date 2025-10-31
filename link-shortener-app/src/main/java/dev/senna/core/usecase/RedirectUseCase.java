package dev.senna.core.usecase;

import dev.senna.core.exception.LinkExpiredException;
import dev.senna.core.exception.LinkNotFoundException;
import dev.senna.core.port.in.RedirectPortIn;
import dev.senna.core.port.out.AnalyticsRepositoryPortOut;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RedirectUseCase implements RedirectPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;
    private final AnalyticsRepositoryPortOut analyticsRepositoryPortOut;

    public RedirectUseCase(LinkRepositoryPortOut linkRepositoryPortOut, AnalyticsRepositoryPortOut analyticsRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
        this.analyticsRepositoryPortOut = analyticsRepositoryPortOut;
    }

    @Override
    public String execute(String linkId) {

        var link = linkRepositoryPortOut.findByLinkId(linkId)
                .orElseThrow(LinkNotFoundException::new);

        if (!link.isActive()) {
            throw new LinkExpiredException();
        }

        if (link.getExpirationDateTime() != null && link.getExpirationDateTime().isBefore(LocalDateTime.now()) ) {
            throw new LinkExpiredException();
        }

        analyticsRepositoryPortOut.updateClickCount(link);

        return link.generateFullUrl();
    }
}
