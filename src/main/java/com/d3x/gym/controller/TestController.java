package com.d3x.gym.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API Rest para gestão de acesso ao GYM-SYS")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @ApiOperation(value = "Retorna o conteúdo visível ao público.")
    @GetMapping("/all")
    public String allAccess() {
        return "Conteúdo Público.";
    }

    @ApiOperation(value = "Retorna o conteúdo visível somente a usuários autenticados no sistema.")
    @GetMapping("/user")
    @PreAuthorize("hasRole('USUARIO') or hasRole('RECEPCIONISTA') or hasRole('GERENTE') or hasRole('ADMIN')")
    public String userAccess() {
        return "Conteúdo do Usuário.";
    }

    @ApiOperation(value = "Retorna o conteúdo visível somente a usuários autenticados que possuam o perfil: Recepcionista.")
    @GetMapping("/mod")
    @PreAuthorize("hasRole('RECEPCIONISTA')")
    public String moderatorAccess() {
        return "Conteúdo restrito ao Recepcionista.";
    }

    @ApiOperation(value = "Retorna o conteúdo visível somente a usuários autenticados que possuam o perfil: Administrador.")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')" )
    public String adminAccess() {
        return "Conteúdo restrito a Administração.";
    }
}