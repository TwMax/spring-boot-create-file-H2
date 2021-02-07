package dev.krzysztof.cardownload.services;

import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

public interface OpenCSVFileServices {
    CSVReader getEndDecode (MultipartFile file);
}
