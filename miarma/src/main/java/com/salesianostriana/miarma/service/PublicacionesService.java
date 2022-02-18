package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.dto.GetPublicacionDto;
import com.salesianostriana.miarma.dto.PublicacionDtoConverter;
import com.salesianostriana.miarma.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.miarma.errores.excepciones.SingleEntityNotFoundException;
import com.salesianostriana.miarma.errores.excepciones.StorageException;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.repos.PublicacionRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.service.base.StorageService;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicacionesService extends BaseService<Publicacion, Long, PublicacionRepository> {


    private final PublicacionRepository repository;
    private final StorageService storageService;
    private final PublicacionDtoConverter converter;
    private final UserEntityService userEntityService;

    public Publicacion create(MultipartFile file, CreatePublicacionDto p, UserEntity user) throws Exception {
        String filename = storageService.store(file);

        List<String> formats = List.of("png", "jpg");
        String video = ("mp4");

        //Si el fichero tiene el fotmato png o jpglo guardará
        if (formats.contains(StringUtils.getFilenameExtension(filename))) {
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
                    .user(user)
                    .multimediaScale(uriScale)
                    .build();
            return repository.save(nuevaP);

            //Si el fichero tiene un formato de video permitirá introductirlo sin la propiedad de escalarlo ya que no la tiene
        }else if(video.contains(StringUtils.getFilenameExtension(filename))){
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();
            Publicacion nuevaP = Publicacion.builder()
                    .titulo(p.getTitulo())
                    .texto(p.getTexto())
                    .privada(p.isPrivada())
                    .multimedia(uri)
                    .user(user)
                    .build();
            return repository.save(nuevaP);
        }else{
            throw new StorageException("El fichero subido está vacío");
        }

    }

    public Publicacion edit(CreatePublicacionDto p, UserEntity user, Long id, MultipartFile file) {
        Optional<Publicacion> publicacion = repository.findById(id);
        String filename = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        if (publicacion.isPresent()) {
            Publicacion publiEncontrada = publicacion.get();
            publiEncontrada.setId(p.getId());
            publiEncontrada.setTitulo(p.getTitulo());
            publiEncontrada.setTexto(p.getTexto());
            publiEncontrada.setPrivada(p.isPrivada());
            publiEncontrada.setMultimedia(uri);

            return repository.save(publiEncontrada);
        } else {
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
        }
    }

    public void deleteById(Long id, UserEntity user) throws IOException {
        Optional<Publicacion> publicacion = repository.findById(id);

        if (publicacion.isPresent()) {
            Publicacion publiEncontrada = publicacion.get();

            if (publiEncontrada.getUser().getNick().equals(user.getNick())) {
                String nombreFichero = StringUtils.cleanPath(publiEncontrada.getMultimedia());
                String[] arrayNombre = nombreFichero.split("/");
                storageService.deleteFile(arrayNombre[arrayNombre.length - 1]);
                repository.deleteById(id);
            }
        } else
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
    }

    public Optional<Publicacion> findOne(Long id, UserEntity user) {
        Optional<Publicacion> publicacion = repository.findById(id);


        if (publicacion.isPresent()) {
            Publicacion publiEncontrada = publicacion.get();
            //Si la publi existe

            if (!publiEncontrada.getUser().isPrivado()
                    || !publicacion.get().isPrivada()) { //Si el usuario es publico y la publi tambien
                return repository.findById(id);
            } else if (publiEncontrada.getUser().getNick().equals(user.getNick())) {
                return repository.findById(id);
            }


        } else {
            throw new SingleEntityNotFoundException(id.toString(), Publicacion.class);
        }
    return Optional.empty();
    }

    public List<GetPublicacionDto> findAllDto() {
        return repository.findByPrivadaIsFalse().stream().map(converter::publicacionToGetPublicacionDto).collect(Collectors.toList());
    }
}
