package dev.senna.core.usecase;

import dev.senna.core.exception.LinkNotFoundException;
import dev.senna.core.port.in.RedirectPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

@Component
public class RedirectUseCase implements RedirectPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public RedirectUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
    }

    @Override
    public String execute(String linkId) {

        var link = linkRepositoryPortOut.findByLinkId(linkId)
                .orElseThrow(LinkNotFoundException::new);

        return link.generateFullUrl();
    }
}
