package com.d3x.gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos")
    private String cep;
    @NotBlank(message = "Nome da rua deve ser preenchido")
    private String rua;
    @NotBlank(message = "Numero deve ser preenchido")
    private String numero;
    private String complemento;
    @NotBlank(message = "Bairro deve ser preenchido")
    private String bairro;
    @NotBlank(message = "Cidade deve ser preenchida")
    private String cidade;
    @NotBlank(message = "Estado deve ser preenchido")
    private String uf;
}
