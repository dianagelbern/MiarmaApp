package com.salesianostriana.miarma.controller;

import com.salesianostriana.miarma.dto.CreatePublicacionDto;
import com.salesianostriana.miarma.dto.GetPublicacionDto;
import com.salesianostriana.miarma.dto.PublicacionDtoConverter;
import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.service.PublicacionesService;
import com.salesianostriana.miarma.users.dto.CreateUserDto;
import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Validated
public class PublicacionController {

    private final PublicacionesService service;
    private final PublicacionDtoConverter converter;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetPublicacionDto> create(@RequestPart("file") MultipartFile file, @RequestPart("publicacion") CreatePublicacionDto dto, @AuthenticationPrincipal UserEntity user) throws Exception{
        Publicacion nuevaP = service.create(file, dto, user);
        GetPublicacionDto nuevaPDto = converter.publicacionToGetPublicacionDto(nuevaP, userDtoConverter.convertUserToGetUserDto(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPublicacionDto> edit(@RequestPart("publicacion") CreatePublicacionDto dto, @AuthenticationPrincipal UserEntity user, @PathVariable Long id, @RequestPart("file") MultipartFile file){
        Publicacion nuevaP = service.edit(dto, user, id, file);
        GetPublicacionDto nuevaPDto = converter.publicacionToGetPublicacionDto(nuevaP, userDtoConverter.convertUserToGetUserDto(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPublicacionDto> findById(@PathVariable Long id){

        Optional<GetPublicacionDto> p = service.findAOne(id).map(publicacion -> {
                    UserEntity user = publicacion.getUser();
                    GetUserDto userDto = userDtoConverter.convertUserToGetUserDto(user);
                    return  converter.publicacionToGetPublicacionDto(publicacion, userDto);
                }
        );
        return ResponseEntity.of(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
