package com.salesianostriana.miarma.dto;

import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetPublicacionDto {
    private Long id;
    private String titulo;
    private String texto;
    private String multimedia;
    private boolean privada;
    private GetUserDto autor;
    private String multimediaScale;
}
