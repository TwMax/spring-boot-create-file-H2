package dev.krzysztof.cardownload.validators;

import dev.krzysztof.cardownload.exception.CarIncomingException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class CarValidator {

    public void validationOfIncomingData(MultipartFile file, String color) {

        if(Objects.isNull(file) || Objects.isNull(color)) {
            throw CarIncomingException.getCarIncomingException();
        }

    }
}
