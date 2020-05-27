package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.ClienteRepository;
import com.d3x.gym.repository.PagamentoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Api(value = "API Rest para consultas e gerenciamento de pagamentos do cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @ApiOperation(value = "Retorna os últimos 3 pagamentos realizados pelo cliente.")
    @GetMapping("/{idCliente}")
    public @ResponseBody
    ArrayList<Pagamento> listaPagamentosCliente(@PathVariable(value = "idCliente") Long id) {
        Optional<Cliente> cliente = clienteRepo.findById(id);
        //Rever este codigo
        if (cliente.isPresent() ) {
            return pagamentoRepo.findLastPagamentoCliente(cliente.get());
        }
        return null;
    }

    @ApiOperation(value = "Solicita a busca por um pagamento por id.")
    @GetMapping("/{idPagamento}")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Optional<Pagamento> pagamentoPorId(@RequestParam(value = "idPagamento") Long id) {
        return pagamentoRepo.findById(id);
    }

    @ApiOperation(value = "Retorna os pagamentos realizados pelo cliente no período buscado.")
    @GetMapping("/buscar/")
    //@RequestMapping(value = "/buscar/", method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<Pagamento> findByClienteAndDataPagamentoBetween(@RequestParam(value = "idCliente") Long id,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        return pagamentoRepo.findByCliente_IdAndDataPagamentoBetween(id, dataInicial, dataFinal);
    }

}
