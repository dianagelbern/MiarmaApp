package com.salesianostriana.miarma.errores.excepciones;

public class RepeatedElementsException extends RuntimeException{

    public RepeatedElementsException(Class clase) {
        super(String.format("No pueden haber dos elementos %s repetidos en una misma ruta.", clase));
    }
}
