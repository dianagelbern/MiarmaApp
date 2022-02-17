package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.errores.excepciones.ElementosRepetidosException;
import com.salesianostriana.miarma.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.miarma.errores.excepciones.SingleEntityNotFoundException;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.models.SeguimientoPK;
import com.salesianostriana.miarma.repos.SeguimientoRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeguimientoService extends BaseService<Seguimiento, SeguimientoPK, SeguimientoRepository> {

    private final SeguimientoRepository seguimientoRepository;
    private final UserEntityService userEntityService;

    public Seguimiento enviarSolicitud(UserEntity usuarioLogueado, String nick){
        Optional<UserEntity> usuarioASeguir = userEntityService.findByUserNick(nick);

        UserEntity usuario = new UserEntity();

        Seguimiento solicitudDeSeguimiento = Seguimiento
                .builder()
                .seguidor(usuarioLogueado)
                .build();

        if (usuarioASeguir.isPresent()){
            usuario = usuarioASeguir.get();
            solicitudDeSeguimiento.setSeguido(usuario);
        }else {
            throw new ElementosRepetidosException(Seguimiento.class);
        }
        if (!usuario.getSeguimientos().contains(solicitudDeSeguimiento)){
            solicitudDeSeguimiento.setSeguimiento(false);
            seguimientoRepository.save(solicitudDeSeguimiento);
            usuario.getSeguimientos().add(solicitudDeSeguimiento);

            userEntityService.save(usuario);
        }
        return solicitudDeSeguimiento;
    }
}
