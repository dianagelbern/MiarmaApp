package com.salesianostriana.miarma.repos;

import com.salesianostriana.miarma.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {


}
