package com.d3x.gym.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.d3x.gym.model.ERole;
import com.d3x.gym.model.Perfil;
import com.d3x.gym.model.Usuario;
import com.d3x.gym.payload.request.LoginRequest;
import com.d3x.gym.payload.request.SignupRequest;
import com.d3x.gym.payload.response.JwtResponse;
import com.d3x.gym.payload.response.MessageResponse;
import com.d3x.gym.repository.PerfilRepository;
import com.d3x.gym.repository.UsuarioRepository;
import com.d3x.gym.security.jwt.JwtUtils;
import com.d3x.gym.security.service.DadosUsuarioImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API Rest Autenticação")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PerfilRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ApiOperation(value = "Solicita a autenticação para um usuário existente no sistema.")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        DadosUsuarioImpl userDetails = (DadosUsuarioImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @ApiOperation(value = "Solicita o registro de um novo usuário ao sistema.")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erro: Nome de usuário já existe!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erro: Email já registrado!"));
        }

        // Create new user's account
        Usuario user = new Usuario(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Perfil> roles = new HashSet<>();

        if (strRoles == null) {
            Perfil userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Erro: Perfil não encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Perfil adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erro: Perfil não encontrado."));
                        roles.add(adminRole);

                        break;
                    case "recepcionista":
                        Perfil recepcionistaRole = roleRepository.findByName(ERole.ROLE_RECEPCIONISTA)
                                .orElseThrow(() -> new RuntimeException("Erro: Perfil não encontrado."));
                        roles.add(recepcionistaRole);

                        break;
                    case "gerente":
                        Perfil gerenteRole = roleRepository.findByName(ERole.ROLE_GERENTE)
                                .orElseThrow(() -> new RuntimeException("Erro: Perfil não encontrado."));
                        roles.add(gerenteRole);

                        break;

                    default:
                        Perfil userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Erro: Perfil não encontrado."));
                        roles.add(userRole);
                }
            });
        }

        user.setPerfils(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso!"));
    }
}