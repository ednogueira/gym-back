package com.d3x.gym.repository;

import com.d3x.gym.model.ERole;
import com.d3x.gym.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByName(ERole name);
}
