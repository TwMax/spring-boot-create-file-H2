package dev.Krzysztof.cardownload.services.impl;

import dev.Krzysztof.cardownload.domain.Car;
import dev.Krzysztof.cardownload.repository.CarRepository;
import dev.Krzysztof.cardownload.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class FileServiceImpl implements FileService {

        private CarRepository carRepository;

        public FileServiceImpl(CarRepository carRepository) {
                this.carRepository = carRepository;
        }

        @Override
        @Transactional
        public void save(MultipartFile file, String color) throws IOException  {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes, "Cp1250");
                String[] rows = completeData.split("\r\r\n");

                for (int i = 1; i < rows.length; i++) {
                        String[] columns = rows[i].split(",");
                        if (columns[3].equals(color)) {
                                Car car = new Car();
                                car.setName(columns[1]);
                                car.setDateOfPurchase(LocalDate.parse(columns[2], formatter));
                                car.setColor(columns[3]);
                                carRepository.save(car);
                        }
                }
        }
}
