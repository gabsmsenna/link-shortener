package dev.senna.core.port.in;

import dev.senna.core.domain.Link;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.domain.PaginatedResult;

public interface UserLinksPortIn {

    PaginatedResult<Link> execute(
            String userId,
            String nextToken,
            int limit,
            LinkFilter filters);
}
