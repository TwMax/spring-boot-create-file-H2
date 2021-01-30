package dev.Krzysztof.cardownload.controller;

import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.services.FileCarDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private FileCarDataService fileCarDataService;

    public CarController(FileCarDataService fileCarDataService) {
        this.fileCarDataService = fileCarDataService;
    }

    @RequestMapping(path = "/fileupload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("color") String color,
                                              @RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        try {
            fileCarDataService.saveCarData(file, color);
        } catch (RuntimeException | IOException e) {
            throw e;
        }

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }


}
