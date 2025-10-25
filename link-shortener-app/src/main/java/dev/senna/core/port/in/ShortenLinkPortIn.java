package dev.senna.core.port.in;

import dev.senna.adapter.in.web.dto.request.ShortenLinkRequest;
import dev.senna.adapter.in.web.dto.response.ShortenLinkResponse;
import dev.senna.core.domain.Link;
import dev.senna.core.usecase.ShortenLinkUseCase;

public interface ShortenLinkPortIn {

    String execute(Link req);
}
