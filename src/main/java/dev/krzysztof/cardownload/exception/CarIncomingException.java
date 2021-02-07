package dev.krzysztof.cardownload.exception;

import dev.krzysztof.cardownload.constants.MessageConstant;
import org.springframework.http.HttpStatus;

public class CarIncomingException extends ApplicationRuntimeException {
    private static final long serialVersionUID = 1L;

    public CarIncomingException() {
        super(HttpStatus.BAD_REQUEST, MessageConstant.WRONG_PARAMETERS);
    }

}
