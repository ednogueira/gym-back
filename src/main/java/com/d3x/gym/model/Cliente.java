package com.d3x.gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_CLIENTE", uniqueConstraints = @UniqueConstraint(columnNames = "CPF"))
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NOME")
    private String nome;

    @NotBlank
    @Column(name = "SOBRENOME")
    private String sobrenome;

    @NotBlank
    @Column(name = "CPF")
    private String cpf;

    @NotBlank
    @Column(name = "RG")
    private String rg;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cep", column = @Column(name = "CEP")),
            @AttributeOverride(name = "rua", column = @Column(name = "RUA")),
            @AttributeOverride(name = "numero", column = @Column(name = "NUMERO")),
            @AttributeOverride(name = "complemento", column = @Column(name = "COMPLEMENTO")),
            @AttributeOverride(name = "bairro", column = @Column(name = "BAIRRO")),
            @AttributeOverride(name = "cidade", column = @Column(name = "CIDADE")),
            @AttributeOverride(name = "uf", column = @Column(name = "UF"))
    })
    @Column(name = "ENDERECO")
    private Endereco endereco;

    @NotBlank
    @Column(name = "TELEFONE")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PLANO")
    private EPlano tipoPlano;

    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_VALIDADE")
    private Instant validadePlano;

    @Column(name = "SALDO_FERIAS")
    private Integer saldoFerias;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "ULTIMA_MODIFICACAO")
    private Instant ultimaModificacao;
}
