package com.marau.marau.controller;

import com.marau.marau.dto.AluguelResponseDTO;
import com.marau.marau.model.Aluguel;
import com.marau.marau.model.Cliente;
import com.marau.marau.model.Quarto;

import com.marau.marau.repository.AluguelRepository;
import com.marau.marau.repository.ClienteRepository;
import com.marau.marau.repository.QuartoRepository;
<<<<<<< HEAD
import com.marau.marau.service.HospedagemService;
=======
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542

import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD
import java.util.NoSuchElementException;
=======
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542

@CrossOrigin("*")
@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private final AluguelRepository aluguelRepository;
    private final ClienteRepository clienteRepository;
    private final QuartoRepository quartoRepository;
<<<<<<< HEAD
    private final HospedagemService hospedagemService;
=======
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542

    public AluguelController(
            AluguelRepository aluguelRepository,
            ClienteRepository clienteRepository,
<<<<<<< HEAD
            QuartoRepository quartoRepository,
            HospedagemService hospedagemService) {
=======
            QuartoRepository quartoRepository) {
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542

        this.aluguelRepository = aluguelRepository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
<<<<<<< HEAD
        this.hospedagemService = hospedagemService;
=======
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542
    }

    @GetMapping
    public List<AluguelResponseDTO> listar() {
<<<<<<< HEAD
        return aluguelRepository.findAll()
                .stream()
                .map(aluguel -> new AluguelResponseDTO(aluguel.getId(), aluguel.getValorTotal()))
                .toList();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Aluguel> historicoPorCliente(@PathVariable Long clienteId) {
        return aluguelRepository.findByClienteId(clienteId);
    }

    @PostMapping
    public AluguelResponseDTO salvar(@RequestBody Aluguel aluguel) {
        Cliente cliente = clienteRepository.findById(aluguel.getCliente().getId())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado."));

        Quarto quarto = quartoRepository.findById(aluguel.getQuarto().getId())
                .orElseThrow(() -> new NoSuchElementException("Quarto não encontrado."));

        aluguel.setCliente(cliente);
        aluguel.setQuarto(quarto);
        aluguel.setStatus("CONFIRMADO");

        hospedagemService.validarAluguel(aluguel);
        aluguel.calcularValorTotal();

        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
        return new AluguelResponseDTO(aluguelSalvo.getId(), aluguelSalvo.getValorTotal());
    }

    @PutMapping("/{id}/cancelar")
    public Aluguel cancelar(@PathVariable Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Aluguel não encontrado."));
        aluguel.setStatus("CANCELADO");
        return aluguelRepository.save(aluguel);
    }
}
=======

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
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542
