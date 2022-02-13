package com.salesianostriana.miarma.errores.excepciones;

public class ElementosRepetidosException extends RuntimeException{

    public ElementosRepetidosException(Class clazz) {
        super(String.format("No se puede agregar el elemento %s ya que existe actualmente en la lista", clazz.getName()));
    }
}
