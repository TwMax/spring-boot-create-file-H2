package dev.Krzysztof.cardownload.services.impl;

import com.opencsv.exceptions.CsvValidationException;
import dev.Krzysztof.cardownload.exception.CarIncomingException;
import dev.Krzysztof.cardownload.exception.CarNotFoundException;
import dev.Krzysztof.cardownload.model.Car;
import dev.Krzysztof.cardownload.repository.CarRepository;
import dev.Krzysztof.cardownload.util.CarUtil;
import dev.Krzysztof.cardownload.validators.CarValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FileCarDataServiceImplTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OpenCSVFileServicesImpl openCSVFileServices;

    private FileCarDataServiceImpl fileCarDataService;

    @Mock
    private CarValidator carValidator;


    @BeforeEach
    void setUp() {
        fileCarDataService = new FileCarDataServiceImpl(carRepository, openCSVFileServices, carValidator);
    }

    @Test()
    void shouldExceptionValidatorOfIncomingDataFileIsNull() {
        // given
        doThrow(CarIncomingException.class).when(carValidator).validationOfIncomingData(null, "żółty");

        // then
        assertThrows(CarIncomingException.class, () -> carValidator.validationOfIncomingData(null, "żółty"));
    }


    @Test()
    void shouldExceptionValidatorIfColorAndFileIsNull() {
        // given
        doThrow(CarIncomingException.class).when(carValidator).validationOfIncomingData(null, null);

        // then
        assertThrows(CarIncomingException.class, () -> carValidator.validationOfIncomingData(null, null));
    }

    @Test
    void shouldExceptionNotFoundCarValidator() {
        // given
        doThrow(CarNotFoundException.class).when(carValidator).validationIfTheCarHasBeenFound(new ArrayList<>());

        // then
        assertThrows(CarNotFoundException.class, () -> carValidator.validationIfTheCarHasBeenFound(new ArrayList<>()));
    }

    @Test
    void shouldParameterColorNotBeCaseSensitive() {
        // given
        final String COLOR = "Żółty";
        Car car1 = Car.builder().color("Żółty").dateOfPurchase(null).name(null).id(null).build();
        Car car2 = Car.builder().color("żółty").dateOfPurchase(null).name(null).id(null).build();
        Car car3 = Car.builder().color("żÓłTy").dateOfPurchase(null).name(null).id(null).build();
        List<Car> cars = Arrays.asList(car1, car2, car3);

        // when
        List<Car> carList = fileCarDataService.filteredCarDataByColor(COLOR, cars);

        //then
        assertEquals(carList.size(), cars.size());
    }

    @Test
    void shouldParameterColorNotBeFound() {
        // given
        final String COLOR = "Zielony";
        Car car1 = Car.builder().color("Żółty").dateOfPurchase(null).name(null).id(null).build();
        Car car2 = Car.builder().color("żółty").dateOfPurchase(null).name(null).id(null).build();
        Car car3 = Car.builder().color("żÓłTy").dateOfPurchase(null).name(null).id(null).build();
        List<Car> cars = Arrays.asList(car1, car2, car3);

        // when
        List<Car> carList = fileCarDataService.filteredCarDataByColor(COLOR, cars);

        //then
        assertEquals(carList.size(), 0);
    }

    @Test
    void shouldMapperNextRecordIsTrue() throws CsvValidationException {
        //given
        String[] nextRecord = {"1", "Ford Focus", "01.12.2005", "Czarny"};

        //when
        Car car = fileCarDataService.mapToCar(nextRecord);

        //then
        assertNull(car.getId());
        assertEquals(car.getName(), "Ford Focus");
        assertEquals(car.getColor(), "Czarny");
    }

    @Test
    void shouldParsedStringToDate() {
        //given
        final String date = "01.12.2005";

        //when
        LocalDate localDate = CarUtil.parseStringToDate(date);

        //then
        assertEquals(localDate.toString(), "2005-12-01");
    }

    @Test
    void shouldExceptionIfCsvValidationIsFalse() throws CsvValidationException {
        //given
        String[] nextRecord = {"\\\\"};
        doThrow(CsvValidationException.class).when(carValidator).validationOfCsvFile(nextRecord);

        // then
        assertThrows(CsvValidationException.class, () -> carValidator.validationOfCsvFile(nextRecord));
    }

    @Test
    @Transactional
    void givenCarToAddShouldReturnAddedCar() {
        //given
        Car car1 = Car.builder()
                .name("Ford Focus")
                .dateOfPurchase(CarUtil.parseStringToDate("02.02.2005"))
                .color("czerwony")
                .build();

        //when
        carRepository.save(car1);
        List<Car> cars = carRepository.findAll();
        //then
        assertEquals(car1.getName(), cars.get(0).getName());
        assertEquals(car1.getColor(), cars.get(0).getColor());
    }
}