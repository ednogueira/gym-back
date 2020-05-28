package com.d3x.gym.controller;

import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.PagamentoRepository;
import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Api(value = "API Rest para consultas e gerenciamento de pagamentos do cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @ApiOperation(value = "Retorna os últimos 3 pagamentos realizados pelo cliente.")
    @GetMapping("/{idCliente}")
    @JsonView(View.Main.class)
    public ResponseEntity<List<Pagamento>> listaPagamentosCliente(@PathVariable(value = "idCliente") Long id) {
        List<Pagamento> pagamentoList = pagamentoRepo.findLastPagamentoCliente(id);
        if (pagamentoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Pagamento>>(pagamentoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Solicita a busca por um pagamento por id.")
    @GetMapping("/{idPagamento}")
    @JsonView(View.All.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Optional<Pagamento>> buscarPagamentoPorId(@RequestParam(value = "idPagamento") Long id) {
        Optional<Pagamento> pagamento = pagamentoRepo.findById(id);
        if (pagamento.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Pagamento>>(pagamento, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna os pagamentos realizados pelo cliente no período buscado.")
    @GetMapping("/buscar/")
    @JsonView(View.Alternative.class)
    public ResponseEntity<List<Pagamento>> buscarPagamentoPorData(@RequestParam(value = "idCliente") Long id,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Pagamento> pagamentoList = pagamentoRepo.findByCliente_IdAndDataPagamentoBetween(id, dataInicial, dataFinal);
        if (pagamentoList.isEmpty()){
            return new ResponseEntity<>(pagamentoList,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Pagamento>>(pagamentoList, HttpStatus.OK);
    }

}
