package com.d3x.gym.repository;

import com.d3x.gym.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**Busca por id/matricula do Cliente*/

    Optional<Cliente> findById(Long id);

    /**Busca por cpf do cliente**/

    Optional<Cliente> findByCpf(String cpf);

    /**Lista os últimos 5 clientes modificados no sistema**/

    @Query(value = "SELECT * FROM GYM_CLIENTE c order by c.ULTIMA_MODIFICACAO desc limit 5", nativeQuery = true)
    ArrayList<Cliente> findByLastClientesModificados();

}
