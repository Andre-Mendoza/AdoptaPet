package com.adoptaPet.repository;

import com.adoptaPet.domain.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
        List<Solicitud> findByUsuario_IdUsuario(Integer idUsuario);
}
