package dev.krzysztof.cardownload.exception;

import org.springframework.http.HttpStatus;

public class ApplicationRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;


    public ApplicationRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
