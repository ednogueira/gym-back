package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "API Rest para consultas e gerenciamento da clientes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

        @Autowired
        private ClienteRepository clienteRepo;

        @ApiOperation(value = "Retorna os últimos 5 clientes modificados no sistema.")
        @GetMapping("/modificados")
        public ResponseEntity<List<Cliente>> listaClientesModificados() {
                List<Cliente> clienteList = clienteRepo.findByLastClientesModificados();
                if (clienteList.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<List<Cliente>>(clienteList,HttpStatus.OK);
        }

        @ApiOperation(value = "Solicita a busca do cliente por id/matricula.")
        @GetMapping(value = "/{id}")
        public ResponseEntity<Optional<Cliente>> buscarClientePorId(@PathVariable(value = "id") Long id) {
                Optional<Cliente> cliente = clienteRepo.findById(id);
                if (cliente.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Optional<Cliente>>(cliente,HttpStatus.OK);
        }

        @ApiOperation(value = "Solicita a busca do cliente por CPF.")
        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<Optional<Cliente>> buscarClientePorCpf(@RequestParam(value = "cpf") String cpf) {
                Optional<Cliente> cliente = clienteRepo.findByCpf(cpf);
                if (cliente.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Optional<Cliente>>(cliente,HttpStatus.OK);
        }

}
