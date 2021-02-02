package dev.krzysztof.cardownload.services.impl;

import com.opencsv.CSVReader;
import dev.krzysztof.cardownload.services.OpenCSVFileServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class OpenCSVFileServicesImpl implements OpenCSVFileServices {

    public CSVReader getEndDecode(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream(), "Cp1250");
        return new CSVReader(reader);
    }

}
