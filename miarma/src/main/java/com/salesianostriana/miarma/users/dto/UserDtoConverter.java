package com.salesianostriana.miarma.users.dto;

import com.salesianostriana.miarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public GetUserDto convertUserToGetUserDto(UserEntity u){
        return GetUserDto.builder()
                .id(u.getId())
                .nick(u.getNick())
                .email(u.getEmail())
                .fechaNacimiento(u.getFechaNacimiento())
                .avatar(u.getAvatar())
                .privado(u.isPrivado())
                .build();
    }

    public CreateUserDto convertUserToUserDto(UserEntity u){
        return CreateUserDto.builder()
                .id(u.getId())
                .password(u.getPassword())
                .password2(u.getPassword())
                .nick(u.getNick())
                .email(u.getEmail())
                .fechaNacimiento(u.getFechaNacimiento())
                .avatar(u.getAvatar())
                .privado(u.isPrivado())
                .build();
    }
}
