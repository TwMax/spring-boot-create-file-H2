package dev.Krzysztof.cardownload.services.impl;

import dev.Krzysztof.cardownload.exception.BadRequestsException;
import dev.Krzysztof.cardownload.services.FileCarDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileCarDataServiceImplTest {

@Autowired
private FileCarDataService fileCarDataService;

    @Test()
    void shouldExceptionIfFileIsNull() {
        Exception exception = assertThrows(BadRequestsException.class, () -> {
            fileCarDataService.saveCarData(null, "żółty");
        });

        String expectedMessage = "Nieprawidłowe parametry wejściowe color lub plik";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test()
    void shouldExceptionIfColorAndFileIsNull() {
        Exception exception = assertThrows(BadRequestsException.class, () -> {
            fileCarDataService.saveCarData(null, null);
        });

        String expectedMessage = "Nieprawidłowe parametry wejściowe color lub plik";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}