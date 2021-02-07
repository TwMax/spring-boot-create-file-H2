package dev.krzysztof.cardownload.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.model.Car;
import dev.krzysztof.cardownload.repository.CarRepository;
import dev.krzysztof.cardownload.services.FileCarDataService;
import dev.krzysztof.cardownload.services.OpenCSVFileServices;
import dev.krzysztof.cardownload.util.CarUtil;
import dev.krzysztof.cardownload.validators.CarValidator;
import dev.krzysztof.cardownload.validators.CsvValidator;
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

    private final CarRepository carRepository;
    private final OpenCSVFileServices openCSVFileServices;
    private final CarValidator carValidator;
    private final CsvValidator csvValidator;

    @Override
    @Transactional
    public List<Car> saveCarData(MultipartFile file, String color) throws CsvValidationException, IOException {
        carValidator.validationOfIncomingData(file, color);
        List<Car> cars = createdCarsFrom(openCSVFileServices.getEndDecode(file));
        return carRepository.saveAll(filteredCarDataByColor(color, cars));
    }


    public List<Car> createdCarsFrom(CSVReader csvReader) throws CsvValidationException, IOException {
        List<Car> cars = new ArrayList<>();


        skipTheFirstRecords(csvReader);
        String[] nextRecord;

        while ((nextRecord = csvReader.readNext()) != null) {
            cars.add(mapToCar(nextRecord));
            csvReader.skip(1);
        }

        return cars;
    }

    private void skipTheFirstRecords(CSVReader csvReader) throws CsvValidationException, IOException {
        csvValidator.validationOfCsvFile(csvReader.readNext());
        csvReader.skip(1);
    }

    public Car mapToCar(String[] nextRecord) throws CsvValidationException {
        csvValidator.validationOfCsvFile(nextRecord);
        return Car.builder()
                .name(nextRecord[1])
                .dateOfPurchase(CarUtil.parseStringToDate(nextRecord[2]))
                .color(nextRecord[3])
                .build();
    }


    @Override
    public List<Car> filteredCarDataByColor(String color, List<Car> cars) {
        return cars.stream().
                filter(x -> x.getColor().equalsIgnoreCase(color)).
                collect(Collectors.toList());
    }
}