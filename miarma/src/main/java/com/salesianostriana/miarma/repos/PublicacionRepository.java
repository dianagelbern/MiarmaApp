package com.salesianostriana.miarma.repos;

import com.salesianostriana.miarma.dto.GetPublicacionDto;
import com.salesianostriana.miarma.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query("""
           SELECT p
           FROM Publicacion p
           JOIN UserEntity u on(u.id = p.user.id)
            """)
    List<Publicacion> findAllPublicacionByUser();


    List<Publicacion> findByPrivadaIsFalse();
}
