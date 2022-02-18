package com.salesianostriana.miarma.users.services;

import com.salesianostriana.miarma.service.base.BaseService;
import com.salesianostriana.miarma.service.base.StorageService;
import com.salesianostriana.miarma.users.dto.CreateUserDto;
import com.salesianostriana.miarma.users.model.UserEntity;
import com.salesianostriana.miarma.users.model.UserRole;
import com.salesianostriana.miarma.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    public Optional <UserEntity> findByUsername(String userName){
        return this.repositorio.findFirstByEmail(userName);
    }

    public Optional<UserEntity> findByUserNick(String nick){
        return this.repositorio.findFirstByNick(nick);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no se encontró"));
    }


    public UserEntity saveUser (CreateUserDto newUser, MultipartFile file) throws Exception{

        String filename = storageService.store(file);
        BufferedImage original = ImageIO.read(file.getInputStream());
        BufferedImage reescalada = storageService.resizeImage(original, 128, 128);

        ImageIO.write(reescalada, "jpg", Files.newOutputStream(storageService.load(filename)));

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

            UserEntity userEntity = UserEntity.builder()
                    .nick(newUser.getNick())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .fechaNacimiento(newUser.getFechaNacimiento())
                    .role(UserRole.USER)
                    .privado(newUser.isPrivado())
                    .avatar(uri)
                    .build();
            return save(userEntity);

    }

}
