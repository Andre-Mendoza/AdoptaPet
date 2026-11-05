package com.adoptaPet.service;

import com.adoptaPet.domain.Seguimiento;
import com.adoptaPet.repository.SeguimientoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

    @Transactional(readOnly = true)
    public List<Seguimiento> getSeguimientos() {
        return seguimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Seguimiento getSeguimiento(Integer idSeguimiento) {
        return seguimientoRepository.findById(idSeguimiento).orElse(null);
    }

    @Transactional
    public void save(Seguimiento seguimiento) {
        seguimientoRepository.save(seguimiento);
    }

    @Transactional
    public void delete(Integer idSeguimiento) {
        seguimientoRepository.deleteById(idSeguimiento);
    }
}
