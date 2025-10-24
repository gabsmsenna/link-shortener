package dev.senna.core.usecase;

import dev.senna.adapter.in.web.dto.response.ShortenLinkResponse;
import dev.senna.core.domain.Link;
import dev.senna.core.exception.LinkAlreadyExistsException;
import dev.senna.core.port.in.ShortenLinkPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

@Component
public class ShortenLinkUseCase implements ShortenLinkPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public ShortenLinkUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
    }

    @Override
    public String execute(Link link) {

        var linkOpt = linkRepositoryPortOut.findByLinkId(link.getLinkId());

        if (linkOpt.isPresent()) {
            throw new LinkAlreadyExistsException();
        }

        linkRepositoryPortOut.save(link);

        return link.getLinkId();
    }


}
