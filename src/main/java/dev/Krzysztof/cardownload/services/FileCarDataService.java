package dev.Krzysztof.cardownload.services;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileCarDataService {

    void saveCarData(MultipartFile file, String color) throws IOException, CsvValidationException;

}
