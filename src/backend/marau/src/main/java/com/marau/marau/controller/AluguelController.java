package com.marau.marau.controller;

import com.marau.marau.dto.AluguelResponseDTO;
import com.marau.marau.model.Aluguel;
import com.marau.marau.model.Cliente;
import com.marau.marau.model.Quarto;

import com.marau.marau.repository.AluguelRepository;
import com.marau.marau.repository.ClienteRepository;
import com.marau.marau.repository.QuartoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private final AluguelRepository aluguelRepository;
    private final ClienteRepository clienteRepository;
    private final QuartoRepository quartoRepository;

    public AluguelController(
            AluguelRepository aluguelRepository,
            ClienteRepository clienteRepository,
            QuartoRepository quartoRepository) {

        this.aluguelRepository = aluguelRepository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
    }

    @GetMapping
    public List<AluguelResponseDTO> listar() {

        return aluguelRepository.findAll()
                .stream()
                .map(aluguel ->
                        new AluguelResponseDTO(
                                aluguel.getId(),
                                aluguel.getValorTotal()))
                .toList();
    }

    @PostMapping
    public AluguelResponseDTO salvar(
            @RequestBody Aluguel aluguel) {

        Cliente cliente =
                clienteRepository.findById(
                        aluguel.getCliente().getId())
                        .orElseThrow();

        Quarto quarto =
                quartoRepository.findById(
                        aluguel.getQuarto().getId())
                        .orElseThrow();

        aluguel.setCliente(cliente);
        aluguel.setQuarto(quarto);

        aluguel.calcularValorTotal();

        Aluguel aluguelSalvo =
                aluguelRepository.save(aluguel);

        return new AluguelResponseDTO(
                aluguelSalvo.getId(),
                aluguelSalvo.getValorTotal());
    }
}