package dev.Krzysztof.cardownload.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.model.Car;
import dev.Krzysztof.cardownload.repository.CarRepository;
import dev.Krzysztof.cardownload.services.FileCarDataService;
import dev.Krzysztof.cardownload.services.OpenCSVFileServices;
import dev.Krzysztof.cardownload.util.CarUtil;
import dev.Krzysztof.cardownload.validators.CarValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FileCarDataServiceImpl implements FileCarDataService {

    private CarRepository carRepository;
    private OpenCSVFileServices openCSVFileServices;
    private CarValidator carValidator;

    @Override
    @Transactional
    public void saveCarData(MultipartFile file, String color) throws CsvValidationException, IOException {
        carValidator.validationOfIncomingData(file, color);
        List<Car> cars = createdCarsFrom(openCSVFileServices.getEndDecode(file));
        carRepository.saveAll(filteredCarDataByColor(color, cars));
    }


    private List<Car> createdCarsFrom(CSVReader csvReader) throws IOException, CsvValidationException {
        List<Car> cars = new ArrayList<>();


        skipTheFirstRecords(csvReader);
        String[] nextRecord;

        while ((nextRecord = csvReader.readNext()) != null) {
            cars.add(mapToCar(nextRecord));
            csvReader.skip(1);
        }

        return cars;
    }

    private void skipTheFirstRecords(CSVReader csvReader) throws IOException, CsvValidationException {
        carValidator.validationOfCsvFile(csvReader.readNext());
        csvReader.skip(1);
    }

    public Car mapToCar(String[] nextRecord) throws CsvValidationException {
        carValidator.validationOfCsvFile(nextRecord);
        return Car.builder()
                .name(nextRecord[1])
                .dateOfPurchase(CarUtil.parseStringToDate(nextRecord[2]))
                .color(nextRecord[3])
                .build();
    }


    @Override
    public List<Car> filteredCarDataByColor(String color, List<Car> cars) {
        List<Car> carsList = cars.stream().
                filter(x -> x.getColor().equalsIgnoreCase(color)).
                collect(Collectors.toList());
        carValidator.validationIfTheCarHasBeenFound(carsList);
        return carsList;
    }
}