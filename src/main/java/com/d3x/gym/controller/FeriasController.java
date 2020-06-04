package com.d3x.gym.controller;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.EPlano;
import com.d3x.gym.model.Ferias;
import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.ClienteRepository;
import com.d3x.gym.repository.FeriasRepository;
import com.d3x.gym.service.FeriasService;
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

@Api(value = "API Rest para consultas e gerenciamento de férias do cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ferias")
public class FeriasController {

    @Autowired
    private FeriasService feriasService;

    @Autowired
    private ClienteRepository clienteRepository;


    @ApiOperation(value = "Retorna as últimas 3 ferias agendadas para cliente.")
    @GetMapping("/{idCliente}")
    @JsonView(View.Main.class)
    public ResponseEntity<List<Ferias>> listaFeriasCliente(@PathVariable(value = "idCliente") Long id) {
        List<Ferias> feriasList = feriasService.findLastFeriasCliente(id);
        if (feriasList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Ferias>>(feriasList,HttpStatus.OK);
    }

    @ApiOperation(value = "Solicita a busca por uma ferias por id.")
    @GetMapping("/{idFerias}")
    @JsonView(View.All.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Optional<Ferias>> buscarFeriasPorId(@RequestParam(value = "idFerias") Long id) {
        Optional<Ferias> ferias = feriasService.findById(id);
        if (ferias.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Ferias>>(ferias,HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna as ferias agendadas para cliente no período buscado.")
    @GetMapping("/buscar/")
    @JsonView(View.Main.class)
    public ResponseEntity<List<Ferias>> buscarFeriasPorData(@RequestParam(value = "idCliente") Long id,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Ferias> feriasList = feriasService.findByCliente_IdAndDataInicioBetween(id, dataInicial, dataFinal);
        if (feriasList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Ferias>>(feriasList,HttpStatus.OK);
    }

    @PostMapping
    @JsonView(View.Main.class)
    @ApiOperation(value = "Solicita o cadastro de férias para um cliente.")
    ResponseEntity<?> saveFerias(@RequestParam(value = "idCliente") Long idCLiente,
                                    @Valid @RequestBody Ferias ferias) {
        Optional<Cliente> cliente = clienteRepository.findById(idCLiente);
        if (cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente inválido");
        }
        if (cliente.get().getTipoPlano() != EPlano.ANUAL){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não tem direito a ferias: Plano mensal");
        }
        return feriasService.save(ferias, idCLiente);
    }

    @DeleteMapping("/delete/{idFerias}")
    @ApiOperation(value = "Solicita a deleção de um pagamento.")
    public ResponseEntity<?> deleteFerias(@PathVariable(value = "idFerias") Long id) {
        Optional<Ferias> ferias = feriasService.findById(id);
        if (ferias.isPresent()) {
            feriasService.delete(ferias.get());
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
