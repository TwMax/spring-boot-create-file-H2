package dev.krzysztof.cardownload.exception.handler;

import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.exception.ApplicationRuntimeException;
import dev.krzysztof.cardownload.exception.CarIncomingException;
import dev.krzysztof.cardownload.model.response.ErrorResponseModel;
import dev.krzysztof.cardownload.util.DateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ApiExceptionHandler {
    @ExceptionHandler({
            ApplicationRuntimeException.class, CarIncomingException.class, CsvValidationException.class, IOException.class
    })
    public ResponseEntity<ErrorResponseModel> handleException(ApplicationRuntimeException e, HttpServletRequest request) {
        ErrorResponseModel errorModel = new ErrorResponseModel();
        errorModel.setTimestamp(DateUtil.getCurrentDateTime());
        errorModel.setPath(request.getRequestURI());
        errorModel.setStatus(e.getStatus().value());
        errorModel.setMessage(e.getMessage());
        errorModel.setError(e.getStatus().getReasonPhrase());

        return new ResponseEntity<>(errorModel, e.getStatus());
    }
}
