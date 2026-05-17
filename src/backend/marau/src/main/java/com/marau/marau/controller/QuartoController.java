package com.marau.marau.controller;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.QuartoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoRepository repository;

    public QuartoController(QuartoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Quarto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Quarto salvar(@RequestBody Quarto quarto) {

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
}