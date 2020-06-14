package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Api(value = "API Rest para consultas e gerenciamento da clientes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/clientes/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ClienteController {

        @Autowired
        private ClienteRepository clienteRepo;

        @ApiOperation(value = "Retorna os últimos 5 clientes modificados no sistema.")
        @GetMapping("/modificados")
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        public ResponseEntity<List<Cliente>> listaClientesModificados() {
                List<Cliente> clienteList = clienteRepo.findByLastClientesModificados();
                if (clienteList.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<List<Cliente>>(clienteList,HttpStatus.OK);
        }

        @ApiOperation(value = "Solicita a busca do cliente por id/matricula.")
        @GetMapping("/{id}")
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        public ResponseEntity<Optional<Cliente>> buscarClientePorId(@PathVariable(value = "id") Long id) {
                Optional<Cliente> cliente = clienteRepo.findById(id);
                if (cliente.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Optional<Cliente>>(cliente,HttpStatus.OK);
        }

        @ApiOperation(value = "Solicita a busca do cliente por CPF.")
        @RequestMapping(method = RequestMethod.GET)
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        public ResponseEntity<Optional<Cliente>> buscarClientePorCpf(@RequestParam(value = "cpf") String cpf) {
                Optional<Cliente> cliente = clienteRepo.findByCpf(cpf);
                if (cliente.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Optional<Cliente>>(cliente,HttpStatus.OK);
        }

        @PostMapping("/criar")
        @ApiOperation(value = "Solicita o cadastro de um novo cliente.")
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        ResponseEntity<?> saveCliente(@Valid @RequestBody Cliente cliente) throws URISyntaxException {
                if (clienteRepo.findByCpf(cliente.getCpf()).isPresent()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um cliente com o CPF: "
                        + cliente.getCpf());
                }
                cliente.setUltimaModificacao(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("America/Sao_Paulo")));
                Cliente result = clienteRepo.save(cliente);
                return ResponseEntity.created(new URI("/api/cliente/" + result.getId()))
                        .body(result);
        }

        @PutMapping
        @ApiOperation(value = "Solicita a atualização dos dados de um cliente.")
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        ResponseEntity<Cliente> updateCliente(@Valid @RequestBody Cliente cliente) {
                cliente.setUltimaModificacao(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("America/Sao_Paulo")));
                Cliente result = clienteRepo.save(cliente);
                return ResponseEntity.ok().body(result);
        }

        @DeleteMapping("/{id}")
        @ApiOperation(value = "Solicita a deleção de um cliente.")
        @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('GERENTE')")
        public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
                Optional<Cliente> cliente = clienteRepo.findById(id);
                if (cliente.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                clienteRepo.deleteById(id);
                return ResponseEntity.ok().build();
        }



}
