package com.marau.marau.controller;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.QuartoRepository;
import com.marau.marau.service.HospedagemService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoRepository repository;
    private final HospedagemService hospedagemService;

    public QuartoController(QuartoRepository repository, HospedagemService hospedagemService) {
        this.repository = repository;
        this.hospedagemService = hospedagemService;
    }

    @GetMapping
    public List<Quarto> listar(@RequestParam(required = false) TipoQuarto tipo) {
        if (tipo != null) {
            return repository.findByTipo(tipo);
        }
        return repository.findAll();
    }

    @PostMapping
    public Quarto salvar(@RequestBody Quarto quarto) {
        hospedagemService.aplicarRegrasDoQuarto(quarto);
        return repository.save(quarto);
    }
}
