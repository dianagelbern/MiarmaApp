package com.salesianostriana.miarma.controller;

import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.service.SeguimientoService;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
//@RequestMapping("/follow")
@RequiredArgsConstructor
public class SeguimientoController {
    private final SeguimientoService seguimientoService;

    @PostMapping("/follow/{nick}")
    public ResponseEntity<Seguimiento> enviarSolicitudDeSeguimiento(@AuthenticationPrincipal UserEntity user, @PathVariable String nick){
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.enviarSolicitud(user, nick));
    }

    @GetMapping("/follow/list")
    public List<Seguimiento> listarPeticiones(@AuthenticationPrincipal UserEntity user){
        return seguimientoService.listarPeticiones(user);
    }


    @PostMapping("/follow/accept/{id}")
    public ResponseEntity<?> aceptarLASolicitud(@PathVariable("id")UUID id, @AuthenticationPrincipal UserEntity user){
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.aceptarSolicitud(id, user));
    }


    @GetMapping("/profile/{id}")
    public Optional<UserEntity> viewProfile(@PathVariable("id")UUID id, @AuthenticationPrincipal UserEntity user){
        return seguimientoService.viewProfile(id, user);
    }
}
