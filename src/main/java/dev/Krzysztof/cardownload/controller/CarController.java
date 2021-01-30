package dev.Krzysztof.cardownload.controller;

import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.services.FileCarDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/cars")
@AllArgsConstructor
public class CarController {

    private FileCarDataService fileCarDataService;

    @RequestMapping(path = "/fileupload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("color") String color,
                                              @RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {

        fileCarDataService.saveCarData(file, color);

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }

}
