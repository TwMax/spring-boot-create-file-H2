package dev.krzysztof.cardownload.services.impl;

import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.exception.CarIncomingException;
import dev.krzysztof.cardownload.model.Car;
import dev.krzysztof.cardownload.repository.CarRepository;
import dev.krzysztof.cardownload.util.CarUtil;
import dev.krzysztof.cardownload.validators.CarValidator;
import dev.krzysztof.cardownload.validators.CsvValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FileCarDataServiceImplTest {

    public static final String ZOLTY = "żółty";
    public static final String CZARNY = "czarny";

    @Mock
    private CarRepository carRepository;

    @Mock
    private OpenCSVFileServicesImpl openCSVFileServices;

    private FileCarDataServiceImpl fileCarDataService;

    @Mock
    private CarValidator carValidator;

    @Mock
    private CsvValidator csvValidator;


    @BeforeEach
    void setUp() {
        fileCarDataService = new FileCarDataServiceImpl(carRepository, openCSVFileServices, carValidator, csvValidator);
    }

    @Test()
    void shouldExceptionValidatorOfIncomingDataFileIsNull() {
        // given
        doThrow(CarIncomingException.class).when(carValidator).validationOfIncomingData(null, ZOLTY);

        // then
        assertThrows(CarIncomingException.class, () -> carValidator.validationOfIncomingData(null, ZOLTY));
    }


    @Test()
    void shouldExceptionValidatorIfColorAndFileIsNull() {
        // given
        doThrow(CarIncomingException.class).when(carValidator).validationOfIncomingData(null, null);

        // then
        assertThrows(CarIncomingException.class, () -> carValidator.validationOfIncomingData(null, null));
    }

    @Test
    void shouldParameterColorNotBeCaseSensitive() {
        // given
        Car car1 = Car.builder().color(ZOLTY).dateOfPurchase(null).name(null).id(null).build();
        Car car2 = Car.builder().color("ŻółTY").dateOfPurchase(null).name(null).id(null).build();
        Car car3 = Car.builder().color("żÓłTy").dateOfPurchase(null).name(null).id(null).build();
        List<Car> cars = Arrays.asList(car1, car2, car3);

        // when
        List<Car> carList = fileCarDataService.filteredCarDataByColor(ZOLTY, cars);

        //then
        assertEquals(carList.size(), cars.size());
    }

    @Test
    void shouldParameterColorNotBeFound() {
        // given
        final String COLOR = "Zielony";
        Car car1 = Car.builder().color(ZOLTY).dateOfPurchase(null).name(null).id(null).build();
        Car car2 = Car.builder().color(ZOLTY).dateOfPurchase(null).name(null).id(null).build();
        Car car3 = Car.builder().color("żÓłTy").dateOfPurchase(null).name(null).id(null).build();
        List<Car> cars = Arrays.asList(car1, car2, car3);

        // when
        List<Car> carList = fileCarDataService.filteredCarDataByColor(COLOR, cars);

        //then
        assertEquals(0,carList.size());
    }

    @Test
    void shouldMapperNextRecordIsTrue() throws CsvValidationException {
        //given
        String[] nextRecord = {"1", "Ford Focus", "01.12.2005", CZARNY};

        //when
        Car car = fileCarDataService.mapToCar(nextRecord);

        //then
        assertNull(car.getId());
        assertEquals("Ford Focus", car.getName());
        assertEquals(CZARNY, car.getColor());
    }

    @Test
    void shouldParsedStringToDate() {
        //given
        final String date = "01.12.2005";

        //when
        LocalDate localDate = CarUtil.parseStringToDate(date);

        //then
        assertEquals("2005-12-01", localDate.toString());
    }

    @Test
    void shouldExceptionIfCsvValidationIsFalse() throws CsvValidationException {
        //given
        String[] nextRecord = {"\\\\"};
        doThrow(CsvValidationException.class).when(csvValidator).validationOfCsvFile(nextRecord);

        // then
        assertThrows(CsvValidationException.class, () -> csvValidator.validationOfCsvFile(nextRecord));
    }

    @Test
    void shouldSaveCarDataThenAddedCarsAndColor() throws IOException, CsvValidationException {
        //given
        Car car1 = Car.builder().id(1L).color("Biały").dateOfPurchase(CarUtil.parseStringToDate("10.02.2010")).name("Ford fiesta").build();
        List<Car> cars = Collections.singletonList(car1);
        String file = "Id,Nazwa,Data zakupu,Kolor\n\n1,Ford fiesta,10.01.2010,Biały\n\n1,Jaguar,10.01.2015,Zielony\n\n";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", file.getBytes("Cp1250"));
        String color = "Biały";

        //when
        when(carRepository.saveAll(anyList())).thenReturn(cars);
        when(openCSVFileServices.getEndDecode(any())).thenCallRealMethod();
        List<Car> returnedCars = fileCarDataService.saveCarData(mockMultipartFile, color);

        //then
        verify(carRepository, times(1)).saveAll(anyList());
        assertEquals(returnedCars.get(0).getId(),cars.get(0).getId());
        assertEquals(returnedCars.get(0).getColor(),cars.get(0).getColor());
        assertEquals(returnedCars.get(0).getDateOfPurchase(),cars.get(0).getDateOfPurchase());
        assertEquals(returnedCars.get(0).getName(),cars.get(0).getName());
    }


    @Test
    void shouldReturnAnEmptyListIfItNotFindAColor() {
        //given
        final String color = "xxx";
        Car car1 = Car.builder().id(1L).color("Zielony").dateOfPurchase(CarUtil.parseStringToDate("10.02.2010")).name("Ford fiesta").build();
        List<Car> cars = Collections.singletonList(car1);

        //when
        List<Car> filteredCars = fileCarDataService.filteredCarDataByColor(color, cars);

        //then
        assertEquals(0,filteredCars.size());

    }
}