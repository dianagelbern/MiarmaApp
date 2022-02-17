package com.salesianostriana.miarma.service.base;

import com.salesianostriana.miarma.utils.MediaTypeUrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    MediaTypeUrlResource loadAsResource(String filename);

    void deleteFile(String filename) throws IOException;

    void deleteAll();



    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception;
}
