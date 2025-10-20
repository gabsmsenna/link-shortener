package dev.senna.core.usecase;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.port.in.MyLinksPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

@Component
public class MyLinksUseCase implements MyLinksPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public MyLinksUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
    }

    @Override
    public PaginatedResult<Link> execute(String userId, String nextToken, int limit) {

        return linkRepositoryPortOut.finAllByUserId(userId, nextToken, limit);

    }
}
