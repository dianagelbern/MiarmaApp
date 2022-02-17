package com.salesianostriana.miarma.errores;

import com.salesianostriana.miarma.errores.excepciones.ElementosRepetidosException;
import com.salesianostriana.miarma.errores.excepciones.RepeatedElementsException;
import com.salesianostriana.miarma.errores.excepciones.SingleEntityNotFoundException;
import com.salesianostriana.miarma.errores.modelo.ApiError;
import com.salesianostriana.miarma.errores.modelo.ApiSubError;
import com.salesianostriana.miarma.errores.modelo.ApiValidationSubError;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.buildApiError(ex, status, request, new ArrayList<>());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return buildApiError(ex, status, request,
                ex.getFieldErrors().stream().map(
                        e -> ApiValidationSubError.builder()
                                .objeto(e.getObjectName())
                                .campo(e.getField())
                                .valorRechazado(e.getRejectedValue())
                                .mensaje(e.getDefaultMessage())
                                .build()
                ).collect(Collectors.toList()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){

        return buildApiError(ex, HttpStatus.BAD_REQUEST, request, ex.getConstraintViolations()
                .stream()
                .map( cv -> ApiValidationSubError.builder()
                        .objeto(cv.getRootBeanClass().getSimpleName())
                        .campo(((PathImpl)cv.getPropertyPath()).getLeafNode().asString())
                        .valorRechazado(cv.getInvalidValue())
                        .mensaje(cv.getMessage())
                        .build()).collect(Collectors.toList()));
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request){

        return this.buildApiError(ex, HttpStatus.NOT_FOUND, request, new ArrayList<>());

    }

    @ExceptionHandler({RepeatedElementsException.class})
    public ResponseEntity<?> handleRepeatedElementsException(RepeatedElementsException ex, WebRequest request){
        return this.buildApiError(ex, HttpStatus.BAD_REQUEST, request, new ArrayList<>());
    }


    private ResponseEntity<Object> buildApiError(Exception exception, HttpStatus status, WebRequest request, List<ApiSubError> subErrorList){

        ApiError error = ApiError.builder()
                .estado(status)
                .codigo(status.value())
                .ruta(((ServletWebRequest) request).getRequest().getRequestURI())
                .mensaje(exception.getMessage())
                .apiSubErrors(subErrorList)
                .build();

        return ResponseEntity.status(status).body(error);
    }



}
