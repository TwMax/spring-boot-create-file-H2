package dev.krzysztof.cardownload.model.response;

import dev.krzysztof.cardownload.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private String timestamp;
    private String path;
    private Integer status;
    private String message;

    public ResponseModel(HttpServletRequest request, HttpServletResponse response, String message) {
        this.setTimestamp(DateUtil.getCurrentDateTime());
        this.setPath(request.getRequestURI());
        this.setStatus(response.getStatus());
        this.setMessage(message);
    }
}
