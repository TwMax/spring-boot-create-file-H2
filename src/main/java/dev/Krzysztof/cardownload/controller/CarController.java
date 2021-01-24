package dev.Krzysztof.cardownload.controller;

import dev.Krzysztof.cardownload.exception.BadRequestsException;
import dev.Krzysztof.cardownload.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private FileService fileService;

    public CarController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(path = "/fileupload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("color") String color,
                                              @RequestParam("file") MultipartFile file) throws IOException {

        if (color.isEmpty() || file.isEmpty()) {
            throw new BadRequestsException();
        }

        try {
            fileService.save(file, color);
        } catch (RuntimeException | IOException e) {
            throw e;
        }

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }


}
