package com.d3x.gym.repository;

import com.d3x.gym.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);
}
