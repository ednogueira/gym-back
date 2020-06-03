package com.d3x.gym.service;

import com.d3x.gym.model.Cliente;
import com.d3x.gym.model.EPlano;
import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.ClienteRepository;
import com.d3x.gym.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class PagamentoService {
    public static final String ANUAL = "ANUAL";

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public Optional<Pagamento> findById(Long id){ return pagamentoRepository.findById(id); }

    public List<Pagamento> findByCliente_IdAndDataPagamentoBetween(Long id, LocalDate dataInicial, LocalDate dataFinal) {
        return pagamentoRepository.findByCliente_IdAndDataPagamentoBetween(id, dataInicial, dataFinal);
    }

    public List<Pagamento> findLastPagamentoCliente(Long id) {
        return pagamentoRepository.findLastPagamentoCliente(id);
    }

    public void delete(Pagamento pagamento){
        pagamentoRepository.delete(pagamento);
    }

    public Pagamento save(Pagamento pagamento, Long idCliente, EPlano plano){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (plano == EPlano.ANUAL){
            cliente.get().setTipoPlano(EPlano.ANUAL);
            cliente.get().setValidadePlano(pagamento.getDataPagamento().plusYears(1));
            cliente.get().setSaldoFerias(30);
            clienteRepository.save(cliente.get());
            pagamento.setCliente(cliente.get());
            return pagamentoRepository.save(pagamento);
        }
        cliente.get().setTipoPlano(EPlano.MENSAL);
        cliente.get().setValidadePlano(pagamento.getDataPagamento().plusMonths(1));
        cliente.get().setSaldoFerias(0);
        clienteRepository.save(cliente.get());
        pagamento.setCliente(cliente.get());
        return pagamentoRepository.save(pagamento);
    }
}
