package dev.Krzysztof.cardownload.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void save(MultipartFile file, String color) throws IOException;

}
