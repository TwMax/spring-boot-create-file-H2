package dev.krzysztof.cardownload.services;

import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileCarDataService {

    List<Car> saveCarData(MultipartFile file, String color) throws IOException, CsvValidationException;

    List<Car> filteredCarDataByColor(String color, List<Car> cars);

}
