package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.dto.PublicacionDtoConverter;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.repos.PublicacionRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicacionesService extends BaseService<Publicacion, UUID, PublicacionRepository> {

    /*
    private PublicacionDtoConverter converter;

    public ResponseEntity<?> create(@RequestBody CreatePublicacionDto dto, @AuthenticationPrincipal UserEntity user){
        Publicacion p = converter.createPublicacionDtoToPublicacion(dto);

    }
     */
}
