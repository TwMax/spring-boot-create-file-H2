package dev.Krzysztof.cardownload.exception;

public class BadRequestsException extends RuntimeException{

    private static final String EXCEPTION_MESSAGE  = "Nieprawidłowe parametry wejściowe color lub plik";

    public BadRequestsException() {
        super(EXCEPTION_MESSAGE);
    }

}
