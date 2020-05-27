package com.d3x.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
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
    @Column(name = "ID")
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @NotNull
    @Digits(integer = 5, fraction = 2, message = "Apenas valores no padrão: de 0 até 99999.00")
    @Column(name = "VALOR")
    private BigDecimal valor;

    //@Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGAMENTO")
    private EFormaPagamento formaPagamento;

}
