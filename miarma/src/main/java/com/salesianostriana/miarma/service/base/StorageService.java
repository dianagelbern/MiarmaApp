package com.salesianostriana.miarma.service.base;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);

    void deleteAll();

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception;
}
