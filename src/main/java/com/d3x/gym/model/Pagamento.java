package com.d3x.gym.model;

import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_PAGAMENTO")
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Main.class})
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.All.class})
    @NotEmpty(message = "Deve ser informado o Id do cliente")
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @JsonView({View.Main.class})
    @NotNull
    @Digits(integer = 5, fraction = 2, message = "Apenas valores no padrão: de 0 até 99999.00")
    @Column(name = "VALOR")
    private BigDecimal valor;

    @JsonView({View.Main.class})
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "Data de pagamento não pode ser no futuro")
    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @JsonView({View.Main.class})
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Uma forma de pagamento deve ser informada")
    @Column(name = "FORMA_PAGAMENTO")
    private EFormaPagamento formaPagamento;

}
