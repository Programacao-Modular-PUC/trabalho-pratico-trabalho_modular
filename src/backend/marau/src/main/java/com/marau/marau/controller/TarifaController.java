package com.marau.marau.controller;

import com.marau.marau.enums.TipoTarifa;
import com.marau.marau.tarifa.ContextoTarifa;
import com.marau.marau.tarifa.EstrategiaTarifa;
import com.marau.marau.tarifa.GerenciadorTarifas;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    private final GerenciadorTarifas gerenciadorTarifas = GerenciadorTarifas.getInstance();

    @GetMapping
    public List<Map<String, String>> listar() {
        return gerenciadorTarifas.listarEstrategias()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/simular")
    public Map<String, Object> simular(
            @RequestParam double valorBase,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida,
            @RequestParam(defaultValue = "PADRAO") TipoTarifa tipo) {

        ContextoTarifa contexto = new ContextoTarifa(valorBase, entrada, saida, tipo);
        double valorTotal = gerenciadorTarifas.calcularValorTotal(contexto);

        return Map.of(
                "tipoTarifa", contexto.getTipoTarifa(),
                "valorBase", contexto.getValorDiariaBase(),
                "diarias", contexto.getQuantidadeDiarias(),
                "valorTotal", valorTotal);
    }

    private Map<String, String> toResponse(EstrategiaTarifa estrategia) {
        return Map.of(
                "tipo", estrategia.getTipo().name(),
                "descricao", estrategia.getDescricao());
    }
}
