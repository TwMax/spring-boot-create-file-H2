package dev.krzysztof.cardownload.services.impl;

import com.opencsv.CSVReader;
import dev.krzysztof.cardownload.services.OpenCSVFileServices;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class OpenCSVFileServicesImpl implements OpenCSVFileServices {

    @SneakyThrows
    public CSVReader getEndDecode(MultipartFile file){
            Reader reader = new InputStreamReader(file.getInputStream(), "Cp1250");
            return new CSVReader(reader);
    }

}
