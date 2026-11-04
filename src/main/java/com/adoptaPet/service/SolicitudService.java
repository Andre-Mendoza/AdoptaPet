package com.adoptaPet.service;

import com.adoptaPet.domain.Solicitud;
import com.adoptaPet.repository.SolicitudRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional(readOnly = true)
    public List<Solicitud> getSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Solicitud getSolicitud(Integer idSolicitud) {
        return solicitudRepository.findById(idSolicitud).orElse(null);
    }

    @Transactional
    public void save(Solicitud solicitud) {
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void delete(Integer idSolicitud) {
        solicitudRepository.deleteById(idSolicitud);
    }
}