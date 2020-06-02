package com.d3x.gym.service;

import com.d3x.gym.model.Pagamento;
import com.d3x.gym.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    public Pagamento save(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

    public void delete(Pagamento pagamento){
        pagamentoRepository.delete(pagamento);
    }
}
