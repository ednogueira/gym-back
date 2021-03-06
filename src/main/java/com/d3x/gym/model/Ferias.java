package com.d3x.gym.model;

import com.d3x.gym.view.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_FERIAS")
public class Ferias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Main.class})
    @Column(name = "ID")
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView({View.All.class})
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @JsonView({View.Main.class})
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @FutureOrPresent(message = "Data de inicio das ferias não pode ser no passado")
    @Column(name = "DATA_INICIO")
    private LocalDate dataInicio;

    @JsonView({View.Main.class})
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Data de termino das ferias deve ser no futuro")
    @Column(name = "DATA_TERMINO")
    private LocalDate dataTermino;

/*    @JsonView({View.Main.class})
    @NotBlank(message = "Quantidade de dias de ferias devem ser informados")
    @Column(name = "QUANTIDADE_DIAS")
    private Integer totalDais;
    //private Long totalDias = dataInicio.until(dataTermino, ChronoUnit.DAYS);*/
}
