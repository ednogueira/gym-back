package com.d3x.gym.repository;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.Ferias;
import com.d3x.gym.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

public interface FeriasRepository extends JpaRepository <Ferias, Long> {

    //Busca férias por id/
    Optional<Ferias> findById(Long id);

    //Lista as últimas 3 férais agendadas para o cliente.
    @Query(value = "SELECT * FROM GYM_FERIAS f where f.Cliente = :cliente order by f.DATA_INICIO desc limit 3", nativeQuery = true)
    ArrayList<Pagamento> findLastPagamentoCliente(@Param("cliente") Cliente cliente);

/*    ArrayList<Pagamento> findAllByDataInicioBetween(
            Instant dataIncial,
            Instant dataFinal,
            Cliente cliente
    );*/
}
