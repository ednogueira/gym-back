package com.d3x.gym.repository;

import com.d3x.gym.model.Ferias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeriasRepository extends JpaRepository <Ferias, Long> {

    /**Busca férias por id**/

    Optional<Ferias> findById(Long id);

    /**Lista as últimas 3 férais agendadas para o cliente.**/

    @Query(value = "SELECT * FROM GYM_FERIAS f where f.ID_CLIENTE = :cliente order by f.DATA_INICIO desc limit 3", nativeQuery = true)
    List<Ferias> findLastFeriasCliente(@Param("cliente") Long idCliente);

    /**Lista todas férias agendadas entre o período informado.**/

    List<Ferias> findByCliente_IdAndDataInicioBetween(
            Long cliente,
            LocalDate dataIncial,
            LocalDate dataFinal
    );
}
