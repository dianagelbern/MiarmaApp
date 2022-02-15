package com.salesianostriana.miarma.dto;

import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicacionDtoConverter {



    public GetPublicacionDto publicacionToGetPublicacionDto(Publicacion p, GetUserDto u){
        return GetPublicacionDto.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(p.getMultimedia())
                .multimediaScale(p.getMultimediaScale())
                .autor(u)
                .build();
    }

    public Publicacion createPublicacionDtoToPublicacion(CreatePublicacionDto p){
        return Publicacion.builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimediaScale(p.getMultimediaScale())
                .multimedia(p.getMultimedia()).build();
    }


}
