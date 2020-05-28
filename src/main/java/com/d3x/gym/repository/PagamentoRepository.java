package com.d3x.gym.repository;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    /**Busca pagamento por id**/

    Optional<Pagamento> findById(Long id);

    /**Lista os últimos 3 pagamentos realizados pelo cliente.**/

    @Query(value = "SELECT * FROM GYM_PAGAMENTO p where p.ID_CLIENTE = :cliente order by p.DATA_PAGAMENTO desc limit 3", nativeQuery = true)
    ArrayList<Pagamento> findLastPagamentoCliente(@Param("cliente") Long idCliente);

    /**Lista todos pagamentos realizados durante o período informado.**/

    //@Query(value = "from Pagamento p where p.cliente = :cliente AND p.dataPagamento BETWEEN :dataInicial AND :dataFinal")
    ArrayList<Pagamento> findByCliente_IdAndDataPagamentoBetween(
            Long cliente,
            LocalDate dataIncial,
            LocalDate dataFinal
    );
}
