//modificado por isa
package com.adoptaPet.repository;

import com.adoptaPet.domain.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {

    public boolean existsBySolicitudIdSolicitud(Integer idSolicitud);

}