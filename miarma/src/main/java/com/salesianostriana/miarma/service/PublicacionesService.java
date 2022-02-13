package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.dto.GetPublicacionDto;
import com.salesianostriana.miarma.dto.PublicacionDtoConverter;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.repos.PublicacionRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicacionesService extends BaseService<Publicacion, UUID, PublicacionRepository> {


    private final PublicacionRepository repository;

    public Publicacion create(CreatePublicacionDto p, UserEntity user) {
        Publicacion nuevaP = Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(p.getMultimedia())
                .build();



        nuevaP.addToUser(user);


        return repository.save(nuevaP);
    }

}
