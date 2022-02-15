package com.salesianostriana.miarma.models;

import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Publicacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String texto;
    private String multimedia;
    private String multimediaScale;
    private boolean privada;

    @ManyToOne
    @JoinColumn(name = "autor")
    private UserEntity user;

    public void addToUser(UserEntity u){
        user = u;
        u.getPublicaciones().add(this);
    }


}
