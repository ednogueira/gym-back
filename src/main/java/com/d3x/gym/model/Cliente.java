package com.d3x.gym.model;

import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonView;
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
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_CLIENTE", uniqueConstraints = @UniqueConstraint(columnNames = "CPF"))
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.All.class})
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @JsonView({View.All.class})
    @Column(name = "NOME")
    private String nome;

    @NotBlank
    @JsonView({View.All.class})
    @Column(name = "SOBRENOME")
    private String sobrenome;

    @NotBlank
    @JsonView({View.All.class})
    @Column(name = "CPF")
    private String cpf;

    @NotBlank
    @JsonView({View.All.class})
    @Column(name = "RG")
    private String rg;

    @NotNull
    @JsonView({View.All.class})
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
    @JsonView({View.All.class})
    @Column(name = "TELEFONE")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @JsonView({View.All.class})
    @Column(name = "TIPO_PLANO")
    private EPlano tipoPlano;

    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonView({View.All.class})
    @Column(name = "DATA_VALIDADE")
    private LocalDate validadePlano;

    @JsonView({View.All.class})
    @Column(name = "SALDO_FERIAS")
    private Integer saldoFerias;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonView({View.All.class})
    @Column(name = "ULTIMA_MODIFICACAO")
    private Instant ultimaModificacao;
}
