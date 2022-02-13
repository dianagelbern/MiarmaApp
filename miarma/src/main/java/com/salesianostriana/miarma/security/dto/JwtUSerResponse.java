package com.salesianostriana.miarma.security.dto;

import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUSerResponse extends UserDtoConverter {
    private UUID id;
    private String nick;
    private String email;
    private String role;
    private String fechaNacimiento;
    private String avatar;
    private String token;
    private boolean privado;

}
