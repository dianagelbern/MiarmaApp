package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.dto.GetPublicacionDto;
import com.salesianostriana.miarma.dto.PublicacionDtoConverter;
import com.salesianostriana.miarma.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.repos.PublicacionRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.service.base.StorageService;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicacionesService extends BaseService<Publicacion, Long, PublicacionRepository> {


    private final PublicacionRepository repository;
    private final StorageService storageService;

    public Publicacion create(MultipartFile file, CreatePublicacionDto p, UserEntity user ) {
        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        Publicacion nuevaP = Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(uri)
                .build();
        nuevaP.addToUser(user);
        return repository.save(nuevaP);
    }

    public Publicacion edit(CreatePublicacionDto p, UserEntity user, Long id){
        Optional<Publicacion> publicacion = repository.findById(id);
        if(user.getPublicaciones().contains(publicacion.get())){
            Publicacion nuevaP = Publicacion.builder()
                    .titulo(p.getTitulo())
                    .texto(p.getTexto())
                    .privada(p.isPrivada())
                    .multimedia(p.getMultimedia())
                    .build();
            nuevaP.addToUser(user);
            return repository.save(nuevaP);
        }
        else {
            throw new EntityNotFoundException("No se encontró ninguna publicación con ese id");
        }
    }

    /*
    public Publicacion findAllPublic(){
        List<Publicacion> publicaciones = repository.findAll();
        //if()
    }
     */

}
