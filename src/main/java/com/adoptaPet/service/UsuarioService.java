

package com.adoptaPet.service;

import com.adoptaPet.domain.Usuario;
import com.adoptaPet.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioPorCorreo(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }
}
