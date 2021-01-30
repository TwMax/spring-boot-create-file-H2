package dev.Krzysztof.cardownload.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.entity.CarEntity;
import dev.Krzysztof.cardownload.exception.BadRequestsException;
import dev.Krzysztof.cardownload.repository.CarRepository;
import dev.Krzysztof.cardownload.services.FileCarDataService;
import dev.Krzysztof.cardownload.services.OpenCSVFileServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FileCarDataServiceImpl implements FileCarDataService {

        private CarRepository carRepository;
        private OpenCSVFileServices openCSVFileServices;

        @Override
        @Transactional
        public void saveCarData(MultipartFile file, String color) throws CsvValidationException, IOException {
                if (isNot(color) || isNot(file)) {
                        throw new BadRequestsException();
                }

                List<CarEntity> carEntities = createdEntityFrom(openCSVFileServices.getEndDecode(file));

                if (isFound(carEntities)) {
                        carRepository.saveAll(filteredCarDataByColor(color, carEntities));
                }
        }


        private boolean isFound(List<CarEntity> carEntities) {
                return carEntities.size() > 1;
        }

        private boolean isNot(MultipartFile file) {
                return file == null || file.isEmpty();
        }

        private boolean isNot(String color) {
                return color == null || color.isEmpty();
        }


        private List<CarEntity> createdEntityFrom(CSVReader csvReader) throws IOException, CsvValidationException {
                List<CarEntity> carEntities = new ArrayList<>();

                // Reading Records One by One in a String array
                String[] nextRecord;
                csvReader.readNext();
                csvReader.skip(1);

                while ((nextRecord = csvReader.readNext()) != null) {
                        CarEntity carEntity = CarEntity.builder()
                                .name(nextRecord[1])
                                .dateOfPurchase(LocalDate.parse(nextRecord[2], DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH)))
                                .color(nextRecord[3])
                                .build();
                        carEntities.add(carEntity);
                        csvReader.skip(1);
                }

                return carEntities;
        }

        private List<CarEntity> filteredCarDataByColor(String color, List<CarEntity> carEntities) {
                return carEntities.stream().
                        filter(x -> x.getColor().equalsIgnoreCase(color)).
                        collect(Collectors.toList());

        }

}