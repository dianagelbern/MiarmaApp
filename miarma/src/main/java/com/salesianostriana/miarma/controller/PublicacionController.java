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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Validated
@Transactional
public class PublicacionController {

    private final PublicacionesService service;
    private final PublicacionDtoConverter converter;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetPublicacionDto> create(@RequestPart("file") MultipartFile file, @RequestPart("publicacion") CreatePublicacionDto dto, @AuthenticationPrincipal UserEntity user) throws Exception{
        Publicacion nuevaP = service.create(file, dto, user);
        GetPublicacionDto nuevaPDto = converter.publicacionToGetPublicacionDto(nuevaP);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPublicacionDto> edit(@RequestPart("publicacion") CreatePublicacionDto dto, @AuthenticationPrincipal UserEntity user, @PathVariable Long id, @RequestPart("file") MultipartFile file){
        Publicacion nuevaP = service.edit(dto, user, id, file);
        GetPublicacionDto nuevaPDto = converter.publicacionToGetPublicacionDto(nuevaP);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPublicacionDto> findById(@PathVariable Long id, @AuthenticationPrincipal UserEntity userC){

        Optional<GetPublicacionDto> p = service.findOne(id, userC).map(converter::publicacionToGetPublicacionDto);
        return ResponseEntity.of(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id,  @AuthenticationPrincipal UserEntity userC) throws IOException {
        service.deleteById(id, userC);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public List<GetPublicacionDto> findAll(){
        return service.findAllDto();
    }
}
