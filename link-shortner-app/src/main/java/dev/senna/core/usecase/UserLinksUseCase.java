package dev.senna.core.usecase;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.port.in.UserLinksPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

@Component
public class UserLinksUseCase implements UserLinksPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public UserLinksUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
        this.linkRepositoryPortOut = linkRepositoryPortOut;
    }

    @Override
    public PaginatedResult<Link> execute(String userId,
                                         String nextToken,
                                         int limit,
                                         LinkFilter filter) {

        filter.validate();

        return linkRepositoryPortOut.findAllByUserId(
                userId,
                nextToken,
                limit,
                filter
        );

    }
}
