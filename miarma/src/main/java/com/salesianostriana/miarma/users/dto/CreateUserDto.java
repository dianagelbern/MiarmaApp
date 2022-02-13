package com.salesianostriana.miarma.users.dto;

import lombok.*;

import javax.management.relation.Role;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private UUID id;
    private String nick;
    private String email;
    private String fechaNacimiento;
    private String password;
    private String password2;
    private boolean privado;
    private String avatar;
}
