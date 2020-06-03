package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.EPlano;
import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.ClienteRepository;
import com.d3x.gym.service.PagamentoService;
import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Api(value = "API Rest para consultas e gerenciamento de pagamentos do cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    public static final String ANUAL = "ANUAL";
    public static final String MENSAL = "MENSAL";

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private ClienteRepository clienteRepository;


    @ApiOperation(value = "Retorna os últimos 3 pagamentos realizados pelo cliente.")
    @GetMapping("/{idCliente}")
    @JsonView(View.Main.class)
    public ResponseEntity<List<Pagamento>> listaPagamentosCliente(@PathVariable(value = "idCliente") Long id) {
        List<Pagamento> pagamentoList = pagamentoService.findLastPagamentoCliente(id);
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
        Optional<Pagamento> pagamento = pagamentoService.findById(id);
        if (pagamento.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Pagamento>>(pagamento, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna os pagamentos realizados pelo cliente no período buscado.")
    @GetMapping("/buscar")
    @JsonView(View.Main.class)
    public ResponseEntity<List<Pagamento>> buscarPagamentoPorData(@RequestParam(value = "idCliente") Long id,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Pagamento> pagamentoList = pagamentoService.findByCliente_IdAndDataPagamentoBetween(id, dataInicial, dataFinal);
        if (pagamentoList.isEmpty()){
            return new ResponseEntity<>(pagamentoList,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Pagamento>>(pagamentoList, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(View.Main.class)
    @ApiOperation(value = "Solicita o cadastro de um pagamento para um cliente.")
    ResponseEntity<?> savePagamento(@RequestParam(value = "idCliente") Long idCLiente,
                                    @RequestParam(value = "plano") EPlano plano,
                                    @Valid @RequestBody Pagamento pagamento) {
        Optional<Cliente> cliente = clienteRepository.findById(idCLiente);
        if (cliente.isEmpty() || (plano != EPlano.ANUAL && plano != EPlano.MENSAL)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente inválido ou plano de mensalidade inválido");
        }
        return new ResponseEntity<Pagamento>(pagamentoService.save(pagamento, idCLiente,plano), HttpStatus.CREATED);

    }

    @PutMapping("/{idPagamento}")
    @ApiOperation(value = "Solicita a atualização para um pagamento realizado por cliente.")
    ResponseEntity<?> updatePagamento(@PathVariable(value = "idPagamento") Long idPagamento,
                                      @RequestParam(value = "idCliente") Long idCliente,
                                      @RequestParam(value = "plano") EPlano plano,
                                      @Valid @RequestBody Pagamento pagamento) {
        if (pagamentoService.findById(idPagamento).isEmpty() || clienteRepository.findById(idCliente).isEmpty()
                || (plano != EPlano.ANUAL && plano != EPlano.MENSAL)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pagamento não encontrado ou plano de mensalidade inválido");
        }
        pagamento.setId(pagamentoService.findById(idPagamento).get().getId());
        return new ResponseEntity<Pagamento>(pagamentoService.save(pagamento, idCliente, plano), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{idPagamento}")
    @ApiOperation(value = "Solicita a deleção de um pagamento.")
    public ResponseEntity<?> deletePagamento(@PathVariable(value = "idPagamento") Long id) {
        Optional<Pagamento> pagamento = pagamentoService.findById(id);
        if (pagamento.isPresent()) {
            pagamentoService.delete(pagamento.get());
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
