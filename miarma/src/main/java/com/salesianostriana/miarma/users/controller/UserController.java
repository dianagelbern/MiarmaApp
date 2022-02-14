package com.salesianostriana.miarma.users.controller;

import com.salesianostriana.miarma.users.dto.CreateUserDto;
import com.salesianostriana.miarma.users.dto.GetUserDto;
import com.salesianostriana.miarma.users.dto.UserDtoConverter;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestPart("file") MultipartFile file, @RequestPart("user") CreateUserDto newUser) throws Exception{

        UserEntity saved = userEntityService.saveUser(newUser, file);
        if(saved == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.convertUserToGetUserDto(saved));
        }

    }





}
