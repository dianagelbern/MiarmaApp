package com.salesianostriana.miarma.service;

import com.salesianostriana.miarma.errores.excepciones.ElementosRepetidosException;
import com.salesianostriana.miarma.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.models.SeguimientoPK;
import com.salesianostriana.miarma.repos.SeguimientoRepository;
import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<Seguimiento> listarPeticiones(UserEntity usuarioLogueado){
        return usuarioLogueado.getSeguimientos().stream().filter(m -> !m.isSeguimiento()).collect(Collectors.toList());
    }

    public Seguimiento aceptarSolicitud(UUID id, UserEntity usuarioLogueado){
        Optional<Seguimiento> seguidor = seguimientoRepository.findIdFromSeguido(usuarioLogueado.getId(), id);
        Seguimiento estadoSolicitud;
        if (seguidor.isPresent()){
            estadoSolicitud = seguidor.get();
            estadoSolicitud.setSeguimiento(true);
            return seguimientoRepository.save(estadoSolicitud);
        }else {
            throw new EntityNotFoundException("No existe ninguna petición para ese usuario");
        }
    }

    public Optional<UserEntity> viewProfile(UUID id, UserEntity user){
        Optional<UserEntity> seguimiento = userEntityService.findById(id);

        if (!seguimiento.get().isPrivado()){
                return seguimiento;
        }else {
            throw new EntityNotFoundException("No se encontró al usuario buscado");
        }
    }
}
