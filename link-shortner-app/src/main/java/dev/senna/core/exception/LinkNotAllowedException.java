package dev.senna.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class LinkNotAllowedException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        pb.setTitle("User does not have access to this link analytics");
        pb.setDetail("User can only see the analytics of his own links");

        return pb;
    }
}
