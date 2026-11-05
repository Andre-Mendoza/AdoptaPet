package com.adoptaPet.service;

import com.adoptaPet.domain.Mascota;
import com.adoptaPet.repository.MascotaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Transactional(readOnly = true)
    public List<Mascota> getMascotas() {
        return mascotaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mascota getMascota(Integer idMascota) {
        return mascotaRepository.findById(idMascota).orElse(null);
    }

    @Transactional
    public void save(Mascota mascota) {
        mascotaRepository.save(mascota);
    }

    @Transactional
    public void delete(Integer idMascota) {
        mascotaRepository.deleteById(idMascota);
    }

    @Transactional(readOnly = true)
    public List<Mascota> getMascotasDisponibles() {
        return mascotaRepository.findAll()
                .stream()
                .filter(m -> "Disponible".equalsIgnoreCase(m.getEstado()))
                .limit(3)
                .toList();
    }
}
