package dev.senna.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.Map;

public class BadFilterDateException extends DomainException {

    private Map<String, Object> invalidFields;

    public BadFilterDateException(Map<String, Object> invalidFields) {
        this.invalidFields = invalidFields;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pb.setTitle("Bad filter date exception");
        pb.setDetail("There is a problem with the filter date");

        pb.setProperties(Map.of("validations", invalidFields));

        return pb;
    }
}
