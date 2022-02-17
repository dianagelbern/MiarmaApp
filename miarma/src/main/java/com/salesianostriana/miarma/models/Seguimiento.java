package com.salesianostriana.miarma.models;

import com.salesianostriana.miarma.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Seguimiento implements Serializable {


    @Builder.Default
    @EmbeddedId
    private SeguimientoPK id = new SeguimientoPK();

    @ManyToOne
    @MapsId("seguidor")
    @JoinColumn(name = "seguidor_id")
    private UserEntity seguidor;

    @ManyToOne
    @MapsId("seguido")
    @JoinColumn(name = "seguido_id")
    private UserEntity seguido;

    //Cuando el usuario acepte la petición se vuelve en true
    private boolean seguimiento;

    //Helpers de usuarios seguidos
    public void addToSeguido(UserEntity u){
        seguido = u;
        u.getSeguimientos().add(this);
    }

    public void removeFromSeguido(UserEntity u){
        u.getSeguimientos().remove(this);
        seguido = null;
    }

    //Helppers de usuarios que nos siguen
    public void addToSeguidor(UserEntity u){
        seguidor = u;
        u.getSeguimientos().add(this);
    }

    public void removeFromSeguidor(UserEntity u){
        u.getSeguimientos().remove(this);
        seguidor = null;
    }
}
