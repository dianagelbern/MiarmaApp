package com.salesianostriana.miarma.users.model;


import com.salesianostriana.miarma.models.Publicacion;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String nick;

    private String email;

    private String fechaNacimiento;

    private String avatar;

    private String password;

    private boolean privado;

    @ManyToMany
    private List<UserEntity> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private List<Publicacion> publicaciones = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }


    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
