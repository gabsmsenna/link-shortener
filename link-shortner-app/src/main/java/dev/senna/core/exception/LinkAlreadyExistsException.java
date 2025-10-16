package dev.senna.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class LinkAlreadyExistsException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Link already exists exception");
        pb.setDetail("Already exists a link with this unique slug");

        return pb;
    }
}
