package com.d3x.gym.model;

import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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

    @NotBlank(message = "Nome deve ser preenchido")
    @JsonView({View.All.class})
    @Column(name = "NOME")
    private String nome;

    @NotBlank(message = "Sobrenome deve ser preenchido")
    @JsonView({View.All.class})
    @Column(name = "SOBRENOME")
    private String sobrenome;

    @NotBlank(message = "CPF deve ser preenchido")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    @JsonView({View.All.class})
    @Column(name = "CPF", updatable = false)
    private String cpf;

    @NotBlank(message = "RG deve ser preenchido")
    @JsonView({View.All.class})
    @Column(name = "RG")
    private String rg;

    @NotNull(message = "Endereço deve ser preenchido corretamente.")
    @JsonView({View.All.class})
    @Embedded
    @Valid
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

    @NotBlank(message = "Telefone deve ser preenchido")
    @JsonView({View.All.class})
    @Column(name = "TELEFONE")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @JsonView({View.All.class})
    @Column(name = "TIPO_PLANO")
    private EPlano tipoPlano;

    @JsonView({View.All.class})
    @Column(name = "DATA_VALIDADE")
    private LocalDate validadePlano;

    @JsonView({View.All.class})
    @Column(name = "SALDO_FERIAS")
    private Integer saldoFerias;

    @JsonView({View.All.class})
    @Column(name = "ULTIMA_MODIFICACAO")
    private LocalDateTime ultimaModificacao;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Pagamento> pagamento;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Ferias> ferias;

}
