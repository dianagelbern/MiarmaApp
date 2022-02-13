package com.salesianostriana.miarma.validacion.validadores;

import com.salesianostriana.miarma.validacion.anotaciones.PasswordMatch;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String verifyPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        verifyPasswordField = constraintAnnotation.verifyPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String password = (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(passwordField);
        String verifyPassword = (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(verifyPasswordField);

        return StringUtils.hasText(password) && password.contentEquals(verifyPassword);


    }
}