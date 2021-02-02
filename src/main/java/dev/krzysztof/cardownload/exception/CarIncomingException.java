package dev.krzysztof.cardownload.exception;

import dev.krzysztof.cardownload.constants.MessageConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarIncomingException extends ApplicationRuntimeException {
    private static final long serialVersionUID = 1L;

    public CarIncomingException(HttpStatus status, String message) {
        super(status, message);
    }

    public static CarIncomingException getCarIncomingException() {
        return new CarIncomingException (HttpStatus.BAD_REQUEST, MessageConstant.WRONG_PARAMETERS);
    }

}
