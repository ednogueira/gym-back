package com.d3x.gym.service;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.Ferias;
import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.ClienteRepository;
import com.d3x.gym.repository.FeriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class FeriasService {

    @Autowired
    private FeriasRepository feriasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Ferias> findById(Long id){ return feriasRepository.findById(id); }

    public List<Ferias> findLastFeriasCliente(Long id){ return feriasRepository.findLastFeriasCliente(id); }

    public List<Ferias> findByCliente_IdAndDataInicioBetween(Long cliente, LocalDate dataIncial, LocalDate dataFinal){
        return feriasRepository.findByCliente_IdAndDataInicioBetween(cliente, dataIncial, dataFinal);
    }

    public void delete(Ferias ferias){
        Long totalDiasFerias = ChronoUnit.DAYS.between(ferias.getDataInicio(),ferias.getDataTermino()) + 1;
        Integer saldoFerias = ferias.getCliente().getSaldoFerias();
        Optional<Cliente> cliente = clienteRepository.findById(ferias.getCliente().getId());
        cliente.get().setSaldoFerias(Math.toIntExact(saldoFerias + totalDiasFerias));
        cliente.get().setValidadePlano(cliente.get().getValidadePlano().minusDays(totalDiasFerias));
        feriasRepository.delete(ferias);
    }

    public ResponseEntity<?> save(Ferias ferias, Long idCLiente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCLiente);
        List<Ferias> feriasAgendadas = feriasRepository.findByCliente_IdAndDataInicioBetween(idCLiente,
                cliente.get().getValidadePlano().minusYears(1).minusDays(30-cliente.get().getSaldoFerias()),
                cliente.get().getValidadePlano());
        Long totalDiasFerias = ChronoUnit.DAYS.between(ferias.getDataInicio(),ferias.getDataTermino()) +1;

        Integer saldoFerias = cliente.get().getSaldoFerias();

        if (feriasAgendadas.size() < 3) {
            if (saldoFerias < totalDiasFerias) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não possui saldo de férias suficiente");
            }
            for (Ferias feriasAgendada : feriasAgendadas) {
                if (hasOverlap(feriasAgendada.getDataInicio(), feriasAgendada.getDataTermino(),
                        ferias.getDataInicio(), ferias.getDataTermino())
                        || feriasAgendada.getDataTermino().equals(ferias.getDataInicio().minusDays(1))
                        || feriasAgendada.getDataInicio().equals(ferias.getDataTermino().plusDays(1))) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique por férias agendadas no" +
                            " periodo selecionado. Não são permitidas ferias consecutivas");
                }
            }
            cliente.get().setSaldoFerias(Math.toIntExact(saldoFerias - totalDiasFerias));
            cliente.get().setValidadePlano(cliente.get().getValidadePlano().plusDays(totalDiasFerias));
            clienteRepository.save(cliente.get());
            ferias.setCliente(cliente.get());
            return new ResponseEntity<Ferias>(feriasRepository.save(ferias), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente já possui três férias agendadas");
    }

    public static boolean hasOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start2.isBefore(end1) && end2.isAfter(start1);
    }
}