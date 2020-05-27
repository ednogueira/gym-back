package com.d3x.gym.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GYM_USUARIO",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "LOGIN"),
                @UniqueConstraint(columnNames = "EMAIL")
        })
public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @NotBlank
        @Column(name = "LOGIN")
        @Size(max = 20)
        private String login;

        @NotBlank
        @Column(name = "EMAIL")
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Column(name = "SENHA")
        @Size(max = 120)
        private String senha;

        @Builder.Default
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "GYM_PERFIL_USUARIO",
                   joinColumns = @JoinColumn(name = "ID_USUARIO"),
                   inverseJoinColumns = @JoinColumn(name = "ID_PERFIL"))
        private Set<Perfil> perfils = new HashSet<>();

        public Usuario(String login, String email, String senha) {
                this.login = login;
                this.email = email;
                this.senha = senha;
        }
}
