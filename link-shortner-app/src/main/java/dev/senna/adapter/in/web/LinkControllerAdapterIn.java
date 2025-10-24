package dev.senna.adapter.in.web;

import dev.senna.adapter.in.web.dto.request.ShortenLinkRequest;
import dev.senna.adapter.in.web.dto.response.AnalyticsResponse;
import dev.senna.adapter.in.web.dto.response.ApiResponse;
import dev.senna.adapter.in.web.dto.response.LinkResponse;
import dev.senna.adapter.in.web.dto.response.ShortenLinkResponse;
import dev.senna.core.domain.LinkFilter;
import dev.senna.core.port.in.LinkAnalyticsPortIn;
import dev.senna.core.port.in.UserLinksPortIn;
import dev.senna.core.port.in.RedirectPortIn;
import dev.senna.core.port.in.ShortenLinkPortIn;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/links")
public class LinkControllerAdapterIn {

    private final ShortenLinkPortIn shortenLinkPortIn;

    private final RedirectPortIn redirectPortIn;

    private final UserLinksPortIn myLinksPortIn;

    private final LinkAnalyticsPortIn linkAnalyticsPortIn;

    public LinkControllerAdapterIn(ShortenLinkPortIn shortenLinkPortIn, RedirectPortIn redirectPortIn, UserLinksPortIn myLinksPortIn, LinkAnalyticsPortIn linkAnalyticsPortIn) {
        this.shortenLinkPortIn = shortenLinkPortIn;
        this.redirectPortIn = redirectPortIn;
        this.myLinksPortIn = myLinksPortIn;
        this.linkAnalyticsPortIn = linkAnalyticsPortIn;
    }

    @PostMapping()
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

    @GetMapping()
    public ResponseEntity<ApiResponse<LinkResponse>> userLinks(@RequestParam(name = "nextToken", defaultValue = "") String nextToken,
                                                               @RequestParam(name = "limit", defaultValue = "3") Integer limit,
                                                               @RequestParam(name = "active", required = false) Boolean active,
                                                               @RequestParam(name = "startCreatedAt", required = false) LocalDate startCreatedAt,
                                                               @RequestParam(name = "endCreatedAt", required = false) LocalDate endCreatedAt,
                                                               JwtAuthenticationToken token) {

        var userId = String.valueOf(token.getTokenAttributes().get("sub"));

        var links = myLinksPortIn.execute(userId, nextToken, limit, new LinkFilter(active, startCreatedAt, endCreatedAt));

        return ResponseEntity.ok(
                new ApiResponse<>(
                        links.items().stream().map(LinkResponse::fromDomain).toList(),
                        links.nextToken()
                )
        );
    }

    @GetMapping("/{linkId}/analytics")
    public ResponseEntity<AnalyticsResponse> linkAnalytics(@PathVariable(name = "linkId") String linkId,
                                                           @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                                           @RequestParam(name = "endDate", required = false) LocalDate endDate,
                                                           JwtAuthenticationToken token) {

        var userId = String.valueOf(token.getTokenAttributes().get("sub"));

        var body = linkAnalyticsPortIn.execute(userId, linkId, startDate, endDate);

        return ResponseEntity.ok(body);
    }
}
