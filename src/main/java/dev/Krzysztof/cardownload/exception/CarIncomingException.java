package dev.Krzysztof.cardownload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarIncomingException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE  = "Nieprawidłowe parametry wejściowe color lub plik";

    public CarIncomingException() {
        super(EXCEPTION_MESSAGE);
    }

}
