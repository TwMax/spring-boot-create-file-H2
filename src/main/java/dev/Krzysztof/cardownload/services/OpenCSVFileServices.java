package dev.Krzysztof.cardownload.services;

import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OpenCSVFileServices {
    CSVReader getEndDecode (MultipartFile file) throws IOException;
}
