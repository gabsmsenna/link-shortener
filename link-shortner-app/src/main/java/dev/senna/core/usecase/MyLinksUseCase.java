package dev.senna.core.usecase;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;
import dev.senna.core.port.in.MyLinksPortIn;
import dev.senna.core.port.out.LinkRepositoryPortOut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.util.Objects.isNull;

@Component
public class MyLinksUseCase implements MyLinksPortIn {

    private final LinkRepositoryPortOut linkRepositoryPortOut;

    public MyLinksUseCase(LinkRepositoryPortOut linkRepositoryPortOut) {
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
