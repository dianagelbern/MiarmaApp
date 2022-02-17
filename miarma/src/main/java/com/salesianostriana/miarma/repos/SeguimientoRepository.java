package com.salesianostriana.miarma.repos;

import com.salesianostriana.miarma.models.Publicacion;
import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.models.SeguimientoPK;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, SeguimientoPK> {

    @Query("""
           SELECT S
           FROM Seguimiento S
           JOIN UserEntity u on(u.id = S.seguido.id)
            """)
    List<Seguimiento> findAllSeguimientoByUser();


    @Query("""
            SELECT s
            FROM Seguimiento s
            WHERE s.seguidor = :id1 AND s.seguido = :id2
            """)
    Optional<Seguimiento> findIdFromSeguido(@Param("id1") UUID seguidor, @Param("id2") UUID seguido);
}
