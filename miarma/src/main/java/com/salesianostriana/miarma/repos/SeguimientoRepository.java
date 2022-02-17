package com.salesianostriana.miarma.repos;

import com.salesianostriana.miarma.models.Seguimiento;
import com.salesianostriana.miarma.models.SeguimientoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, SeguimientoPK> {
}
