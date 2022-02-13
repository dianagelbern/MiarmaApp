package com.salesianostriana.miarma.users.dto;

import lombok.*;

import javax.management.relation.Role;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {
    private UUID id;
    private String nick;
    private String email;
    private String fechaNacimiento;
    private String avatar;
    private boolean privado;
}
