package com.marau.marau.controller;

import com.marau.marau.model.Cliente;
import com.marau.marau.repository.ClienteRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(
            ClienteRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente salvar(
            @RequestBody Cliente cliente) {

        return repository.save(cliente);
    }
}