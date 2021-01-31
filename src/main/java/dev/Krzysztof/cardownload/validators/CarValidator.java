package dev.Krzysztof.cardownload.validators;

import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.exception.CarIncomingException;
import dev.Krzysztof.cardownload.exception.CarNotFoundException;
import dev.Krzysztof.cardownload.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Component
public class CarValidator extends RuntimeException {

    public void validationOfIncomingData(MultipartFile file, String color) {

        if(Objects.isNull(file) || Objects.isNull(color)) {
            throw new CarIncomingException();
        }

    }

    public void validationIfTheCarHasBeenFound(List<Car> cars)  {
        if (cars.isEmpty()) {
            throw new CarNotFoundException("Nie znaleziono auta aby zapisać do bazy");
        }
    }

    public void validationOfCsvFile(String[] line) throws CsvValidationException {
        if (line.length != 4) {
            throw new CsvValidationException("Błędny typ pliku");
        }
    }
}
