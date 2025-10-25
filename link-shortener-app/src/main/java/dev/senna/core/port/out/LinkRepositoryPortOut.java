package dev.senna.core.port.out;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LinkRepositoryPortOut {

    Link save(Link link);

    Optional<Link> findByLinkId(String linkId);

    PaginatedResult<Link> findAllByUserId(String userId, String nextToken, int limit, LinkFilter filters);
}
