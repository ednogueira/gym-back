package com.d3x.gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
