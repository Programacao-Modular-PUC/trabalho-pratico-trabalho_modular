package com.marau.marau.controller;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.QuartoRepository;
<<<<<<< HEAD
import com.marau.marau.service.HospedagemService;
=======
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoRepository repository;
<<<<<<< HEAD
    private final HospedagemService hospedagemService;

    public QuartoController(QuartoRepository repository, HospedagemService hospedagemService) {
        this.repository = repository;
        this.hospedagemService = hospedagemService;
=======

    public QuartoController(QuartoRepository repository) {
        this.repository = repository;
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542
    }

    @GetMapping
    public List<Quarto> listar(@RequestParam(required = false) TipoQuarto tipo) {
<<<<<<< HEAD
        if (tipo != null) {
            return repository.findByTipo(tipo);
        }
=======

        if (tipo != null) {
            return repository.findByTipo(tipo);
        }

>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542
        return repository.findAll();
    }

    @PostMapping
    public Quarto salvar(@RequestBody Quarto quarto) {
<<<<<<< HEAD
        hospedagemService.aplicarRegrasDoQuarto(quarto);
        return repository.save(quarto);
    }
=======

        calcularRegras(quarto);

        return repository.save(quarto);
    }

    private void calcularRegras(Quarto quarto) {

        if (quarto.getTipo() == TipoQuarto.INDIVIDUAL) {

            quarto.setCapacidadeHospedes(
                    quarto.getQuantidadeCamas());

            if (quarto.getQuantidadeCamas() > 1) {

                double adicional =
                        (quarto.getQuantidadeCamas() - 1) * 50;

                quarto.setValorBase(
                        quarto.getValorBase() + adicional);
            }

            quarto.setPossuiBerco(false);
        }

        else if (quarto.getTipo() == TipoQuarto.DUPLO) {

            if (quarto.isPossuiBerco()) {

                quarto.setValorBase(
                        quarto.getValorBase() + 80);
            }

            quarto.setCapacidadeHospedes(2);
        }

        else if (quarto.getTipo() == TipoQuarto.FAMILIA) {

            double acrescimo =
                    quarto.getCapacidadeHospedes() * 0.15;

            quarto.setValorBase(
                    quarto.getValorBase()
                    + (quarto.getValorBase() * acrescimo));

            if (quarto.getCapacidadeHospedes() >= 5) {

                quarto.setValorBase(
                        quarto.getValorBase() * 0.90);
            }
        }
    }
>>>>>>> 9c449a0f9abd87e899021217b631445095ed6542
}
