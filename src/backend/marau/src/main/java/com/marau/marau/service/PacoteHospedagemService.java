package com.marau.marau.service;

import com.marau.marau.dto.PacoteHospedagemRequestDTO;
import com.marau.marau.dto.PacoteHospedagemResponseDTO;
import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.model.PacoteHospedagemContratado;
import com.marau.marau.model.Reserva;
import com.marau.marau.pacote.PacoteHospedagem;
import com.marau.marau.pacote.PacoteHospedagemFactory;
import com.marau.marau.repository.PacoteHospedagemContratadoRepository;
import com.marau.marau.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PacoteHospedagemService {

    private final PacoteHospedagemContratadoRepository pacoteRepository;
    private final ReservaRepository reservaRepository;
    private final PacoteHospedagemFactory factory = new PacoteHospedagemFactory();

    public PacoteHospedagemService(
            PacoteHospedagemContratadoRepository pacoteRepository,
            ReservaRepository reservaRepository) {

        this.pacoteRepository = pacoteRepository;
        this.reservaRepository = reservaRepository;
    }

    public Map<String, Object> listarModelosEServicos() {
        return Map.of(
                "modelos", factory.listarModelos(),
                "servicosDisponiveis", factory.listarServicosDisponiveis());
    }

    public List<PacoteHospedagemResponseDTO> listarContratados() {
        return pacoteRepository.findAll()
                .stream()
                .map(PacoteHospedagemResponseDTO::fromEntity)
                .toList();
    }

    public List<PacoteHospedagemResponseDTO> listarPorReserva(Long reservaId) {
        return pacoteRepository.findByReservaId(reservaId)
                .stream()
                .map(PacoteHospedagemResponseDTO::fromEntity)
                .toList();
    }

    public PacoteHospedagemResponseDTO simular(PacoteHospedagemRequestDTO request) {
        DadosPacote dados = montarPacote(request);
        return new PacoteHospedagemResponseDTO(
                dados.pacote(),
                dados.tipoPacote(),
                dados.reserva() == null ? null : dados.reserva().getId());
    }

    public PacoteHospedagemResponseDTO contratar(PacoteHospedagemRequestDTO request) {
        DadosPacote dados = montarPacote(request);
        PacoteHospedagem pacote = dados.pacote();

        PacoteHospedagemContratado entity = new PacoteHospedagemContratado();
        entity.setReserva(dados.reserva());
        entity.setTipoPacote(dados.tipoPacote());
        entity.setValorHospedagem(pacote.getValorHospedagem());
        entity.setValorTotal(pacote.getValorTotal());
        entity.setValorServicos(pacote.getValorTotal() - pacote.getValorHospedagem());
        entity.setServicosAdicionais(pacote.getServicosAdicionais());

        PacoteHospedagemContratado salvo = pacoteRepository.save(entity);
        return PacoteHospedagemResponseDTO.fromEntity(salvo);
    }

    private DadosPacote montarPacote(PacoteHospedagemRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Dados do pacote não informados.");
        }

        Reserva reserva = buscarReserva(request.getReservaId());
        double valorBase = reserva == null ? request.getValorHospedagemBase() : reserva.getValorTotal();
        if (valorBase <= 0) {
            throw new IllegalArgumentException("Informe um valor base de hospedagem maior que zero.");
        }

        TipoPacoteHospedagem tipo = request.getTipoPacote() == null
                ? TipoPacoteHospedagem.PERSONALIZADO
                : request.getTipoPacote();

        PacoteHospedagem pacote = factory.montar(
                tipo,
                valorBase,
                request.getServicosAdicionais());

        return new DadosPacote(pacote, tipo, reserva);
    }

    private Reserva buscarReserva(Long reservaId) {
        if (reservaId == null) {
            return null;
        }
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new NoSuchElementException("Reserva não encontrada."));
    }

    private record DadosPacote(
            PacoteHospedagem pacote,
            TipoPacoteHospedagem tipoPacote,
            Reserva reserva) {
    }
}
