package com.salesianostriana.miarma.users.controller;

import com.salesianostriana.miarma.users.dto.CreateUserDto;
import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newUser){

        UserEntity saved = userEntityService.saveUser(newUser);
        if(saved == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
        }

    }

    /*
    @PostMapping("/auth/register")
    public ResponseEntity<?> nuevoUsuario(@RequestPart("user") CreateUserDto newUser, @RequestPart("file")MultipartFile file){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userEntityService.saveUser(newUser, file));
        /*
        UserEntity saved = userEntityService.saveUser(newUser);
        if(saved == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
        }
        }
     */




}
