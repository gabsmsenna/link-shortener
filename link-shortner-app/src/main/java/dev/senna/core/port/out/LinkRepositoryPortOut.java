package dev.senna.core.port.out;

import dev.senna.core.domain.Link;

import java.util.Optional;

public interface LinkRepositoryPortOut {

    Link save(Link link);

    Optional<Link> findByLinkId(String linkId);
}
