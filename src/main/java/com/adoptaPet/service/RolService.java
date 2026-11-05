package com.adoptaPet.service;

import com.adoptaPet.domain.Rol;
import com.adoptaPet.repository.RolRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rol getRol(Integer idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

    @Transactional
    public void delete(Integer idRol) {
        rolRepository.deleteById(idRol);
    }
}