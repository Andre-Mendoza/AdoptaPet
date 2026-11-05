package com.adoptaPet.repository;

import com.adoptaPet.domain.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
