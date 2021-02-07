package dev.krzysztof.cardownload.controller;

import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.constants.MessageConstant;
import dev.krzysztof.cardownload.model.Car;
import dev.krzysztof.cardownload.model.response.RestResponseModel;
import dev.krzysztof.cardownload.services.FileCarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final FileCarDataService fileCarDataService;

    @PostMapping(path = "/fileupload")
    public ResponseEntity<RestResponseModel> processFile(@RequestParam(required = false) String color,
                                                         @RequestParam(required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException, CsvValidationException {

        List<Car> cars = fileCarDataService.saveCarData(file, color);
        RestResponseModel responseModel = new RestResponseModel(request, response, cars, MessageConstant.OK);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
