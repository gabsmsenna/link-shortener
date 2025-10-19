package dev.senna.core.port.in;

import dev.senna.core.domain.Link;

import java.util.List;

public interface MyLinksPortIn {

    List<Link> execute(String userId);
}
