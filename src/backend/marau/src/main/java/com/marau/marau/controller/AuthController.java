package com.marau.marau.controller;

import com.marau.marau.dto.LoginRequestDTO;
import com.marau.marau.enums.TipoUsuario;
import com.marau.marau.model.Usuario;
import com.marau.marau.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Já existe uma conta com este e-mail.");
        }
        if (usuario.getTipo() == null) {
            usuario.setTipo(TipoUsuario.CLIENTE);
        }
        Usuario salvo = usuarioRepository.save(usuario);
        salvo.setSenha(null);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dados) {
        Usuario usuario = usuarioRepository.findByEmail(dados.getEmail()).orElse(null);
        if (usuario == null || !usuario.getSenha().equals(dados.getSenha())) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
        usuario.setSenha(null);
        return ResponseEntity.ok(usuario);
    }
}
