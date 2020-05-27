package com.d3x.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_FERIAS")
public class Ferias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_INICIO")
    private Instant dataInicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_TERMINO")
    private Instant dataTermino;

    @NotBlank
    @Column(name = "QUANTIDADE_DIAS")
    private Integer totalDais;
    //private Long totalDias = dataInicio.until(dataTermino, ChronoUnit.DAYS);
}
