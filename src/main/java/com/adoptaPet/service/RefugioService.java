package com.adoptaPet.service;

import com.adoptaPet.domain.Refugio;
import com.adoptaPet.repository.RefugioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefugioService {

    private final RefugioRepository refugioRepository;

    public RefugioService(RefugioRepository refugioRepository) {
        this.refugioRepository = refugioRepository;
    }

    @Transactional(readOnly = true)
    public List<Refugio> getRefugios() {
        return refugioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Refugio getRefugio(Integer idRefugio) {
        return refugioRepository.findById(idRefugio).orElse(null);
    }

    @Transactional
    public void save(Refugio refugio) {
        refugioRepository.save(refugio);
    }

    @Transactional
    public void delete(Integer idRefugio) {
        refugioRepository.deleteById(idRefugio);
    }
}