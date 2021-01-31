package dev.Krzysztof.cardownload.services;

import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileCarDataService {

    void saveCarData(MultipartFile file, String color) throws IOException, CsvValidationException;

    List<Car> filteredCarDataByColor(String color, List<Car> cars);

}
