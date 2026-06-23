package com.marau.marau.service;

import com.marau.marau.enums.CategoriaFidelidade;
import com.marau.marau.fidelidade.BeneficioFidelidade;
import com.marau.marau.fidelidade.GerenciadorFidelidade;
import com.marau.marau.fidelidade.ResultadoFidelidade;
import com.marau.marau.model.Cliente;
import com.marau.marau.model.ProgramaFidelidade;
import com.marau.marau.repository.ClienteRepository;
import com.marau.marau.repository.ProgramaFidelidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Serviço responsável pelas operações do Programa de Fidelidade.
 *
 * Delega a lógica de negócio (categorias e benefícios) ao
 * {@link GerenciadorFidelidade} (Singleton), mantendo o serviço responsável
 * apenas pela orquestração com o banco de dados.
 */
@Service
public class FidelidadeService {

    private final ProgramaFidelidadeRepository fidelidadeRepository;
    private final ClienteRepository clienteRepository;

    public FidelidadeService(ProgramaFidelidadeRepository fidelidadeRepository,
                             ClienteRepository clienteRepository) {
        this.fidelidadeRepository = fidelidadeRepository;
        this.clienteRepository = clienteRepository;
    }

    // -------------------------------------------------------------------------
    // Inicialização e consulta
    // -------------------------------------------------------------------------

    /**
     * Busca ou cria o registro de fidelidade de um cliente.
     * Se o cliente ainda não tiver um registro, cria com 0 hospedagens (BRONZE).
     *
     * @param clienteId ID do cliente
     * @return registro de fidelidade do cliente
     */
    @Transactional
    public ProgramaFidelidade buscarOuCriar(Long clienteId) {
        return fidelidadeRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    Cliente cliente = clienteRepository.findById(clienteId)
                            .orElseThrow(() -> new NoSuchElementException(
                                    "Cliente não encontrado: " + clienteId));
                    ProgramaFidelidade novo = new ProgramaFidelidade(cliente);
                    return fidelidadeRepository.save(novo);
                });
    }

    /**
     * Retorna o registro de fidelidade de um cliente.
     *
     * @throws NoSuchElementException se o cliente não possuir registro
     */
    public ProgramaFidelidade buscarPorCliente(Long clienteId) {
        return fidelidadeRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Registro de fidelidade não encontrado para o cliente: " + clienteId));
    }

    // -------------------------------------------------------------------------
    // Atualização após hospedagem
    // -------------------------------------------------------------------------

    /**
     * Registra uma hospedagem concluída, incrementando o contador e
     * recalculando a categoria do cliente.
     *
     * Deve ser chamado no momento do check-out.
     *
     * @param clienteId ID do cliente que realizou a hospedagem
     * @return registro atualizado
     */
    @Transactional
    public ProgramaFidelidade registrarHospedagem(Long clienteId) {
        ProgramaFidelidade fidelidade = buscarOuCriar(clienteId);
        fidelidade.registrarHospedagem();
        return fidelidadeRepository.save(fidelidade);
    }

    // -------------------------------------------------------------------------
    // Consulta de benefícios e cálculo
    // -------------------------------------------------------------------------

    /**
     * Lista os benefícios disponíveis para a categoria atual do cliente.
     *
     * @param clienteId ID do cliente
     * @return lista de benefícios aplicáveis
     */
    public List<BeneficioFidelidade> listarBeneficios(Long clienteId) {
        ProgramaFidelidade fidelidade = buscarOuCriar(clienteId);
        return GerenciadorFidelidade.getInstance()
                .getBeneficiosDisponiveis(fidelidade.getCategoria());
    }

    /**
     * Calcula o valor total de uma reserva aplicando o melhor benefício
     * financeiro disponível para o cliente.
     *
     * @param clienteId    ID do cliente
     * @param valorDiaria  valor base da diária
     * @param totalDiarias número de diárias da reserva
     * @return resultado com categoria, valor final e benefício aplicado
     */
    public ResultadoFidelidade calcularValorComFidelidade(Long clienteId,
                                                          double valorDiaria,
                                                          int totalDiarias) {
        ProgramaFidelidade fidelidade = buscarOuCriar(clienteId);
        return GerenciadorFidelidade.getInstance()
                .calcular(valorDiaria, totalDiarias, fidelidade.getTotalHospedagens());
    }

    /**
     * Retorna a categoria de fidelidade atual do cliente.
     *
     * @param clienteId ID do cliente
     * @return categoria atual
     */
    public CategoriaFidelidade obterCategoria(Long clienteId) {
        return buscarOuCriar(clienteId).getCategoria();
    }
}
