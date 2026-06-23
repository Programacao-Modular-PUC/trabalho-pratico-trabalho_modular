package com.marau.marau.controller;

import com.marau.marau.enums.CategoriaFidelidade;
import com.marau.marau.fidelidade.BeneficioFidelidade;
import com.marau.marau.fidelidade.ResultadoFidelidade;
import com.marau.marau.model.ProgramaFidelidade;
import com.marau.marau.service.FidelidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para o Programa de Fidelidade.
 *
 * Base path: /api/fidelidade
 */
@RestController
@RequestMapping("/api/fidelidade")
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    public FidelidadeController(FidelidadeService fidelidadeService) {
        this.fidelidadeService = fidelidadeService;
    }

    /**
     * GET /api/fidelidade/{clienteId}
     * Retorna o registro de fidelidade do cliente (categoria + total de hospedagens).
     */
    @GetMapping("/{clienteId}")
    public ResponseEntity<ProgramaFidelidade> obterFidelidade(@PathVariable Long clienteId) {
        return ResponseEntity.ok(fidelidadeService.buscarOuCriar(clienteId));
    }

    /**
     * GET /api/fidelidade/{clienteId}/categoria
     * Retorna apenas a categoria atual do cliente.
     */
    @GetMapping("/{clienteId}/categoria")
    public ResponseEntity<Map<String, String>> obterCategoria(@PathVariable Long clienteId) {
        CategoriaFidelidade categoria = fidelidadeService.obterCategoria(clienteId);
        return ResponseEntity.ok(Map.of("categoria", categoria.name()));
    }

    /**
     * GET /api/fidelidade/{clienteId}/beneficios
     * Lista os benefícios disponíveis para a categoria atual do cliente.
     */
    @GetMapping("/{clienteId}/beneficios")
    public ResponseEntity<List<String>> listarBeneficios(@PathVariable Long clienteId) {
        List<BeneficioFidelidade> beneficios = fidelidadeService.listarBeneficios(clienteId);
        List<String> descricoes = beneficios.stream()
                .map(BeneficioFidelidade::getDescricao)
                .toList();
        return ResponseEntity.ok(descricoes);
    }

    /**
     * POST /api/fidelidade/{clienteId}/hospedagem
     * Registra uma hospedagem concluída para o cliente.
     * Deve ser chamado no check-out.
     */
    @PostMapping("/{clienteId}/hospedagem")
    public ResponseEntity<ProgramaFidelidade> registrarHospedagem(@PathVariable Long clienteId) {
        return ResponseEntity.ok(fidelidadeService.registrarHospedagem(clienteId));
    }

    /**
     * GET /api/fidelidade/{clienteId}/calcular?valorDiaria=200&totalDiarias=5
     * Calcula o valor total de uma reserva aplicando o melhor benefício do cliente.
     */
    @GetMapping("/{clienteId}/calcular")
    public ResponseEntity<Map<String, Object>> calcularValor(
            @PathVariable Long clienteId,
            @RequestParam double valorDiaria,
            @RequestParam int totalDiarias) {

        ResultadoFidelidade resultado = fidelidadeService.calcularValorComFidelidade(
                clienteId, valorDiaria, totalDiarias);

        return ResponseEntity.ok(Map.of(
                "categoria",         resultado.getCategoria().name(),
                "valorOriginal",     resultado.getValorOriginal(),
                "valorFinal",        resultado.getValorFinal(),
                "economia",          resultado.getEconomia(),
                "beneficioAplicado", resultado.getBeneficioAplicado() != null
                        ? resultado.getBeneficioAplicado().getDescricao()
                        : "nenhum"
        ));
    }
}
