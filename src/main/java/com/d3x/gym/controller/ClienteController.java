package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        public @ResponseBody ArrayList<Cliente> listaClientesModificados() {
                return clienteRepo.findByLastClientesModificados();
        }

        @ApiOperation(value = "Solicita a busca do cliente por id/matricula.")
        @GetMapping(value = "/{id}")
        public @ResponseBody
        Optional<Cliente> clientePorId(@PathVariable(value = "id") Long id) {
                return clienteRepo.findById(id);
        }

        @ApiOperation(value = "Solicita a busca do cliente por CPF.")
        @RequestMapping(method = RequestMethod.GET)
        public @ResponseBody
        Optional<Cliente> clientePorCpf(@RequestParam(value = "cpf") String cpf) {
                return clienteRepo.findByCpf(cpf);
        }

}
