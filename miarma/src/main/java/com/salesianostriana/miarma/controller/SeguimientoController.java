package com.salesianostriana.miarma.controller;

import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.service.SeguimientoService;
import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class SeguimientoController {
    private final SeguimientoService seguimientoService;

    @PostMapping("/{nick}")
    public ResponseEntity<Seguimiento> enviarSolicitudDeSeguimiento(@AuthenticationPrincipal UserEntity user, @PathVariable String nick){
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.enviarSolicitud(user, nick));
    }
}
