package dev.krzysztof.cardownload.validators;

import com.opencsv.exceptions.CsvValidationException;
import dev.krzysztof.cardownload.constants.MessageConstant;
import org.springframework.stereotype.Component;

@Component
public class CsvValidator {

    public void validationOfCsvFile(String[] line) throws CsvValidationException {
        if (line.length != 4) {
            throw new CsvValidationException(MessageConstant.PARSE_ERROR);
        }
    }
}
