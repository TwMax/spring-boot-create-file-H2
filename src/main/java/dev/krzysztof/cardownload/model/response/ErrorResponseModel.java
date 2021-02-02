package dev.krzysztof.cardownload.model.response;

import dev.krzysztof.cardownload.exception.ApplicationRuntimeException;
import dev.krzysztof.cardownload.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ErrorResponseModel extends ResponseModel {
    private String error;
    private String message;

    public ErrorResponseModel(ApplicationRuntimeException e, HttpServletRequest request) {
        this.setTimestamp(DateUtil.getCurrentDateTime());
        this.setPath(request.getRequestURI());
        this.setStatus(e.getStatus().value());
        this.setMessage(e.getMessage());
        this.setError(e.getStatus().getReasonPhrase());
    }
}
