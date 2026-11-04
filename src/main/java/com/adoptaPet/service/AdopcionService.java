package com.adoptaPet.service;

import com.adoptaPet.domain.Adopcion;
import com.adoptaPet.repository.AdopcionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;

    public AdopcionService(AdopcionRepository adopcionRepository) {
        this.adopcionRepository = adopcionRepository;
    }

    @Transactional(readOnly = true)
    public List<Adopcion> getAdopciones() {
        return adopcionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Adopcion getAdopcion(Integer idAdopcion) {
        return adopcionRepository.findById(idAdopcion).orElse(null);
    }

    @Transactional
    public void save(Adopcion adopcion) {
        adopcionRepository.save(adopcion);
    }

    @Transactional
    public void delete(Integer idAdopcion) {
        adopcionRepository.deleteById(idAdopcion);
    }
}
