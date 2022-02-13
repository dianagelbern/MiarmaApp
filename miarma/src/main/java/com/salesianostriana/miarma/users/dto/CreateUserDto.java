package com.salesianostriana.miarma.users.dto;

import com.salesianostriana.miarma.validacion.anotaciones.PasswordMatch;
import lombok.*;

import javax.management.relation.Role;
import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatch(passwordField = "password",
        verifyPasswordField = "password2",
        message = "{usuario.password.notmatch}")
public class CreateUserDto {

    private UUID id;
    private String nick;
    @Email
    private String email;
    private String fechaNacimiento;
    private String password;
    private String password2;
    private boolean privado;
    private String avatar;
}
