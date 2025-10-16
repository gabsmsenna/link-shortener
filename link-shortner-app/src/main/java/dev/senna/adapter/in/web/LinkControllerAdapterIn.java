package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.ShortenLinkRequest;
import dev.senna.adapter.in.web.dto.response.ShortenLinkResponse;
import dev.senna.core.port.in.ShortenLinkPortIn;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@Validated
public class LinkControllerAdapterIn {

    private final ShortenLinkPortIn shortenLinkPortIn;

    private final Logger logger = LoggerFactory.getLogger(LinkControllerAdapterIn.class);

    public LinkControllerAdapterIn(ShortenLinkPortIn shortenLinkPortIn) {
        this.shortenLinkPortIn = shortenLinkPortIn;
    }

    @PostMapping(value = "/links")
    public ResponseEntity<ShortenLinkResponse> shortenLink(@RequestBody @Valid ShortenLinkRequest req,
                                                           JwtAuthenticationToken token) {

        var userId = UUID.fromString(token.getToken().getSubject());


        var resp = shortenLinkPortIn.execute(req.toDomain(userId));

        return ResponseEntity.created(URI.create(resp.shortenUrl())).body(resp);
    }
}
