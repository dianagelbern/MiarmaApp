package com.salesianostriana.miarma.errores.excepciones;

import com.salesianostriana.miarma.exception.StorageException;

public class FileNotFoundException extends StorageException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
