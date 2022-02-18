package com.salesianostriana.miarma;

import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.service.PublicacionesService;
import com.salesianostriana.miarma.service.SeguimientoService;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Initdata {

    public final UserEntityService userEntityService;
    public final PublicacionesService publicacionesService;
    public final SeguimientoService seguimientoService;

    @PostConstruct
    public void prueba(){

        UserEntity manolo = UserEntity.builder()
                .nick("Manolo")
                .createdAt(LocalDateTime.now())
                .email("manolo@gmail.com")
                .avatar("avatar.png")
                .privado(false)
                .password("micontraseña")
                .fechaNacimiento("26/04/1996")
                .build();
        userEntityService.save(manolo);

        UserEntity diana = UserEntity.builder()
                .nick("Diana")
                .createdAt(LocalDateTime.now())
                .email("diana@gmail.com")
                .avatar("avatar.png")
                .privado(false)
                .password("micontraseña")
                .build();

        userEntityService.save(diana);

        Publicacion pub1 = Publicacion.builder()
                .privada(false)
                .titulo("Publicación de prueba")
                .texto("Esto es un texto de prueba")
                .user(manolo)
                .multimedia("archivoMultimedia.png").build();

        Publicacion pub2 = Publicacion.builder()
                .privada(false)
                .titulo("Publicación de prueba numero 2")
                .texto("Esto es un texto de prueba 2")
                .user(diana)
                .multimedia("archivoMultimedia2.png").build();
        publicacionesService.save(pub1);
        publicacionesService.save(pub2);




        UserEntity hector = UserEntity.builder()
                .nick("Hector")
                .createdAt(LocalDateTime.now())
                .email("hector@gmail.com")
                .avatar("avatar.png")
                .privado(false)
                .password("micontraseña").build();

        userEntityService.save(hector);




    }

}