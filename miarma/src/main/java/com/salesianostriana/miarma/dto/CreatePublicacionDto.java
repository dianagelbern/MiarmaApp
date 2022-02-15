package com.salesianostriana.miarma.dto;

import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreatePublicacionDto {

    private String titulo;
    private String texto;
    private String multimedia;
    private boolean privada;
    private String multimediaScale;
}
