package dev.krzysztof.cardownload.model.response;

import dev.krzysztof.cardownload.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RestResponseModel extends ResponseModel {
    private Object data;

    public RestResponseModel(HttpServletRequest request, HttpServletResponse response, Object data, String message) {
        this.setTimestamp(DateUtil.getCurrentDateTime());
        this.setPath(request.getRequestURI());
        this.setData(data);
        this.setMessage(message);
        this.setStatus(response.getStatus());
    }
}
