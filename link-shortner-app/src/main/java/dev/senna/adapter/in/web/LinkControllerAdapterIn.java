package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.ShortenLinkRequest;
import dev.senna.adapter.in.web.dto.response.ShortenLinkResponse;
import dev.senna.core.port.in.RedirectPortIn;
import dev.senna.core.port.in.ShortenLinkPortIn;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@Validated
public class LinkControllerAdapterIn {

    private final ShortenLinkPortIn shortenLinkPortIn;

    private final RedirectPortIn redirectPortIn;

    public LinkControllerAdapterIn(ShortenLinkPortIn shortenLinkPortIn, RedirectPortIn redirectPortIn) {
        this.shortenLinkPortIn = shortenLinkPortIn;
        this.redirectPortIn = redirectPortIn;
    }

    @PostMapping(value = "/links")
    public ResponseEntity<ShortenLinkResponse> shortenLink(@RequestBody @Valid ShortenLinkRequest req,
                                                           JwtAuthenticationToken token) {

        var userId = UUID.fromString(token.getToken().getSubject());


        var resp = shortenLinkPortIn.execute(req.toDomain(userId));

        return ResponseEntity.created(URI.create(resp.shortenUrl())).body(resp);
    }

    @GetMapping(value = "/{linkId}")
    public ResponseEntity<ShortenLinkResponse> redirect(@PathVariable(name = "linkId") String linkId) {

        var fullUrl = redirectPortIn.execute(linkId);

        if (fullUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(fullUrl));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
