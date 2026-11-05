
package com.adoptaPet.repository;

import com.adoptaPet.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreoElectronico(String correoElectronico);

}
