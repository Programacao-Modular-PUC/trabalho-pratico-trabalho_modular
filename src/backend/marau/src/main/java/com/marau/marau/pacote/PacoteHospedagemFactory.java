package com.marau.marau.pacote;

import com.marau.marau.enums.TipoPacoteHospedagem;
import com.marau.marau.enums.TipoServicoAdicional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PacoteHospedagemFactory {

    public PacoteHospedagem montar(
            TipoPacoteHospedagem tipoPacote,
            double valorHospedagem,
            List<TipoServicoAdicional> servicosPersonalizados) {

        TipoPacoteHospedagem tipo = tipoPacote == null
                ? TipoPacoteHospedagem.PERSONALIZADO
                : tipoPacote;

        PacoteHospedagem pacote = new HospedagemBasica(tipo.getNome(), valorHospedagem);

        for (TipoServicoAdicional servico : selecionarServicos(tipo, servicosPersonalizados)) {
            pacote = adicionarServico(pacote, servico);
        }

        return pacote;
    }

    public List<Map<String, Object>> listarModelos() {
        List<Map<String, Object>> modelos = new ArrayList<>();
        for (TipoPacoteHospedagem tipo : TipoPacoteHospedagem.values()) {
            modelos.add(Map.of(
                    "tipo", tipo.name(),
                    "nome", tipo.getNome(),
                    "servicos", selecionarServicos(tipo, List.of())
                            .stream()
                            .map(TipoServicoAdicional::getNome)
                            .toList()));
        }
        return modelos;
    }

    public List<Map<String, Object>> listarServicosDisponiveis() {
        List<Map<String, Object>> servicos = new ArrayList<>();
        for (TipoServicoAdicional servico : TipoServicoAdicional.values()) {
            servicos.add(Map.of(
                    "tipo", servico.name(),
                    "nome", servico.getNome(),
                    "descricao", servico.getDescricao(),
                    "valor", servico.getValor()));
        }
        return servicos;
    }

    private List<TipoServicoAdicional> selecionarServicos(
            TipoPacoteHospedagem tipo,
            List<TipoServicoAdicional> servicosPersonalizados) {

        List<TipoServicoAdicional> servicos = switch (tipo) {
            case ECONOMICO -> List.of(TipoServicoAdicional.CAFE_DA_MANHA);
            case FAMILIA -> List.of(
                    TipoServicoAdicional.CAFE_DA_MANHA,
                    TipoServicoAdicional.TRANSPORTE,
                    TipoServicoAdicional.LAVANDERIA);
            case PREMIUM -> List.of(
                    TipoServicoAdicional.CAFE_DA_MANHA,
                    TipoServicoAdicional.PASSEIO_TURISTICO,
                    TipoServicoAdicional.TRANSPORTE,
                    TipoServicoAdicional.LAVANDERIA,
                    TipoServicoAdicional.TRASLADO_AEROPORTO);
            case PERSONALIZADO -> servicosPersonalizados == null
                    ? List.of()
                    : servicosPersonalizados;
        };

        Set<TipoServicoAdicional> semRepeticao = new LinkedHashSet<>(servicos);
        return new ArrayList<>(semRepeticao);
    }

    private PacoteHospedagem adicionarServico(
            PacoteHospedagem pacote,
            TipoServicoAdicional servico) {

        if (servico == null) {
            return pacote;
        }

        return switch (servico) {
            case CAFE_DA_MANHA -> new CafeDaManhaDecorator(pacote);
            case PASSEIO_TURISTICO -> new PasseioTuristicoDecorator(pacote);
            case TRANSPORTE -> new TransporteDecorator(pacote);
            case LAVANDERIA -> new LavanderiaDecorator(pacote);
            case TRASLADO_AEROPORTO -> new TrasladoAeroportoDecorator(pacote);
        };
    }
}
