package com.salesianostriana.miarma.dto;

import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicacionDtoConverter {

    private final UserDtoConverter userDtoConverter;

    public GetPublicacionDto publicacionToGetPublicacionDto(Publicacion p, UserEntity u){
        return GetPublicacionDto.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(p.getMultimedia())
                .autor(userDtoConverter.convertUserToGetUserDto(u))
                .build();
    }

    public Publicacion createPublicacionDtoToPublicacion(CreatePublicacionDto p){
        return Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(p.getMultimedia()).build();
    }
}
