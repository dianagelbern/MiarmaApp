package com.salesianostriana.miarma.errores.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApiError {

    private HttpStatus estado;
    private int codigo;
    private String mensaje;
    private String ruta;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> apiSubErrors;

/*

    public ApiError(HttpStatus estado, int codigo, String mensaje, String ruta, LocalDateTime fecha) {
        this.estado = estado;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.fecha = fecha;
    }


    public ApiError(HttpStatus estado, String mensaje, String ruta, List<ApiSubError> subErrors) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.subErrors = subErrors;
    }


    public ApiError(HttpStatus status, String message, String requestURI) {
    }
 */

}
