package com.marau.marau.dto;

import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.enums.TipoServicoAdicional;
import com.marau.marau.model.PacoteHospedagemContratado;
import com.marau.marau.pacote.PacoteHospedagem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PacoteHospedagemResponseDTO {

    private Long id;
    private Long reservaId;
    private TipoPacoteHospedagem tipoPacote;
    private double valorHospedagem;
    private double valorServicos;
    private double valorTotal;
    private List<TipoServicoAdicional> servicosAdicionais = new ArrayList<>();
    private List<String> descricoesServicos = new ArrayList<>();

    public PacoteHospedagemResponseDTO() {
    }

    public PacoteHospedagemResponseDTO(
            PacoteHospedagem pacote,
            TipoPacoteHospedagem tipoPacote,
            Long reservaId) {

        this(null, pacote, tipoPacote, reservaId);
    }

    public PacoteHospedagemResponseDTO(
            Long id,
            PacoteHospedagem pacote,
            TipoPacoteHospedagem tipoPacote,
            Long reservaId) {

        this.id = id;
        this.reservaId = reservaId;
        this.tipoPacote = tipoPacote;
        this.valorHospedagem = arredondar(pacote.getValorHospedagem());
        this.valorTotal = arredondar(pacote.getValorTotal());
        this.valorServicos = arredondar(valorTotal - valorHospedagem);
        this.servicosAdicionais = pacote.getServicosAdicionais();
        this.descricoesServicos = pacote.getDescricoesServicos();
    }

    public static PacoteHospedagemResponseDTO fromEntity(PacoteHospedagemContratado entity) {
        PacoteHospedagemResponseDTO dto = new PacoteHospedagemResponseDTO();
        dto.id = entity.getId();
        dto.reservaId = entity.getReserva() == null ? null : entity.getReserva().getId();
        dto.tipoPacote = entity.getTipoPacote();
        dto.valorHospedagem = entity.getValorHospedagem();
        dto.valorServicos = entity.getValorServicos();
        dto.valorTotal = entity.getValorTotal();
        dto.servicosAdicionais = entity.getServicosAdicionais();
        dto.descricoesServicos = entity.getServicosAdicionais()
                .stream()
                .map(servico -> servico.getNome() + " - R$ " + String.format("%.2f", servico.getValor()).replace('.', ','))
                .toList();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public TipoPacoteHospedagem getTipoPacote() {
        return tipoPacote;
    }

    public double getValorHospedagem() {
        return valorHospedagem;
    }

    public double getValorServicos() {
        return valorServicos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public List<TipoServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }

    public List<String> getDescricoesServicos() {
        return descricoesServicos;
    }

    private static double arredondar(double valor) {
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
