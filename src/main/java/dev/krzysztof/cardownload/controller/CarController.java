package dev.krzysztof.cardownload.controller;

import dev.krzysztof.cardownload.constants.MessageConstant;
import dev.krzysztof.cardownload.exception.ApplicationRuntimeException;
import dev.krzysztof.cardownload.model.Car;
import dev.krzysztof.cardownload.model.response.RestResponseModel;
import dev.krzysztof.cardownload.services.FileCarDataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@AllArgsConstructor
public class CarController {

    @Autowired
    private final FileCarDataService fileCarDataService;

    @RequestMapping(path = "/fileupload", method = RequestMethod.POST)
    public ResponseEntity<RestResponseModel> processFile(@RequestParam(required = false) String color,
                                                     @RequestParam(required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            List<Car> cars = fileCarDataService.saveCarData(file, color);
            RestResponseModel responseModel = new RestResponseModel(request, response, cars, MessageConstant.OK);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } catch (ApplicationRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

}
