package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.miarma.errores.excepciones.SingleEntityNotFoundException;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.repos.PublicacionRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.service.base.StorageService;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicacionesService extends BaseService<Publicacion, Long, PublicacionRepository> {


    private final PublicacionRepository repository;
    private final StorageService storageService;

    public Publicacion create(MultipartFile file, CreatePublicacionDto p, UserEntity user ) throws Exception {
        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        String filenameScale = storageService.store(file);
        BufferedImage original = ImageIO.read(file.getInputStream());
        BufferedImage reescalada = storageService.resizeImage(original, 128, 128);
        ImageIO.write(reescalada, "jpg", Files.newOutputStream(storageService.load(filenameScale)));

        String uriScale = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        Publicacion nuevaP = Publicacion.builder()
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .privada(p.isPrivada())
                .multimedia(uri)
                .multimediaScale(uriScale)
                .build();
        nuevaP.addToUser(user);
        return repository.save(nuevaP);
    }

    public Publicacion edit(CreatePublicacionDto p, UserEntity user, Long id, MultipartFile file){
        Optional<Publicacion> publicacion = repository.findById(id);

        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        if(publicacion.isPresent()){
            Publicacion publiEncontrada = publicacion.get();
            publiEncontrada = Publicacion.builder()
                    .id(p.getId())
                    .titulo(p.getTitulo())
                    .texto(p.getTexto())
                    .privada(p.isPrivada())
                    .multimedia(uri)
                    .build();
            return repository.save(publiEncontrada);
        }
        else {
            throw new EntityNotFoundException("No se encontró ninguna publicación con ese id");
        }
    }

    public void deleteById(Long id){
        Optional<Publicacion> publicacion = repository.findById(id);
        if(publicacion.isPresent()) {
            Publicacion publiEncontrada = publicacion.get();
            repository.deleteById(id);
        }else{
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
        }

    }

    public Optional<Publicacion> findAOne(Long id){
        Optional<Publicacion> publicacion = repository.findById(id);
        Publicacion publiEncontrada = publicacion.get();

        if(publicacion.isPresent()) {
            //if(publiEncontrada.getUser().isPrivado() == false ){
                return repository.findById(id);
            //}
        }else{
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
        }
    }


}
