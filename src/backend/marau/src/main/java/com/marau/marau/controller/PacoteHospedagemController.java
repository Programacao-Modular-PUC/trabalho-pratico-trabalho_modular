package com.marau.marau.controller;

import com.marau.marau.dto.PacoteHospedagemRequestDTO;
import com.marau.marau.dto.PacoteHospedagemResponseDTO;
import com.marau.marau.service.PacoteHospedagemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/pacotes")
public class PacoteHospedagemController {

    private final PacoteHospedagemService service;

    public PacoteHospedagemController(PacoteHospedagemService service) {
        this.service = service;
    }

    @GetMapping("/modelos")
    public Map<String, Object> listarModelosEServicos() {
        return service.listarModelosEServicos();
    }

    @GetMapping
    public List<PacoteHospedagemResponseDTO> listarContratados() {
        return service.listarContratados();
    }

    @GetMapping("/reserva/{reservaId}")
    public List<PacoteHospedagemResponseDTO> listarPorReserva(@PathVariable Long reservaId) {
        return service.listarPorReserva(reservaId);
    }

    @PostMapping("/simular")
    public PacoteHospedagemResponseDTO simular(@RequestBody PacoteHospedagemRequestDTO request) {
        return service.simular(request);
    }

    @PostMapping
    public PacoteHospedagemResponseDTO contratar(@RequestBody PacoteHospedagemRequestDTO request) {
        return service.contratar(request);
    }
}
