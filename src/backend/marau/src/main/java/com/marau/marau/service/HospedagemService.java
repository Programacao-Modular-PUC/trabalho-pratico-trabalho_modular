package com.marau.marau.service;

import com.marau.marau.enums.TipoTarifa;
import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.exception.CapacidadeExcedidaException;
import com.marau.marau.exception.DataInvalidaException;
import com.marau.marau.exception.QuartoIndisponivelException;
import com.marau.marau.exception.RecursoNaoPermitidoException;
import com.marau.marau.model.Aluguel;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.AluguelRepository;
import com.marau.marau.tarifa.ContextoTarifa;
import com.marau.marau.tarifa.GerenciadorTarifas;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HospedagemService {

    private final AluguelRepository aluguelRepository;

    public HospedagemService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public Quarto aplicarRegrasDoQuarto(Quarto quarto) {
        validarQuarto(quarto);

        if (quarto.getTipo() == TipoQuarto.INDIVIDUAL) {
            quarto.setCapacidadeHospedes(1);
            if (quarto.isPossuiBerco()) {
                throw new RecursoNaoPermitidoException("Berço não é permitido em quarto individual.");
            }
        }

        if (quarto.getTipo() == TipoQuarto.DUPLO) {
            quarto.setCapacidadeHospedes(2);
            if (quarto.isPossuiBerco()) {
                quarto.setValorBase(quarto.getValorBase() + 80);
            }
        }

        if (quarto.getTipo() == TipoQuarto.FAMILIA) {
            if (quarto.getCapacidadeHospedes() < 3) {
                throw new CapacidadeExcedidaException("Quarto família precisa ter capacidade mínima de 3 hóspedes.");
            }
            double acrescimo = quarto.getCapacidadeHospedes() * 0.15;
            quarto.setValorBase(quarto.getValorBase() + (quarto.getValorBase() * acrescimo));
            if (quarto.getCapacidadeHospedes() >= 5) {
                quarto.setValorBase(quarto.getValorBase() * 0.90);
            }
        }

        return quarto;
    }

    public double calcularDiariaPorTipo(Quarto quarto) {
        Quarto copia = new Quarto();
        copia.setTipo(quarto.getTipo());
        copia.setValorBase(quarto.getValorBase());
        copia.setPossuiBerco(quarto.isPossuiBerco());
        copia.setCapacidadeHospedes(quarto.getCapacidadeHospedes());
        copia.setQuantidadeCamas(quarto.getQuantidadeCamas());
        return aplicarRegrasDoQuarto(copia).getValorBase();
    }

    public void validarAluguel(Aluguel aluguel) {
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não informado.");
        }
        if (aluguel.getCliente() == null || aluguel.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente não informado.");
        }
        if (aluguel.getQuarto() == null || aluguel.getQuarto().getId() == null) {
            throw new IllegalArgumentException("Quarto não informado.");
        }
        validarDatas(aluguel.getDataEntrada(), aluguel.getDataSaida());
        validarCapacidade(aluguel.getQuantidadeHospedes(), aluguel.getQuarto().getCapacidadeHospedes());
        validarDisponibilidade(aluguel.getQuarto().getId(), aluguel.getDataEntrada(), aluguel.getDataSaida());
    }

    public void validarDatas(LocalDate entrada, LocalDate saida) {
        if (entrada == null || saida == null) {
            throw new DataInvalidaException("Data de entrada e saída são obrigatórias.");
        }
        if (!saida.isAfter(entrada)) {
            throw new DataInvalidaException("A data de saída precisa ser depois da data de entrada.");
        }
    }

    public void validarCapacidade(int quantidadeHospedes, int capacidadeQuarto) {
        if (quantidadeHospedes <= 0) {
            throw new CapacidadeExcedidaException("Quantidade de hóspedes precisa ser maior que zero.");
        }
        if (quantidadeHospedes > capacidadeQuarto) {
            throw new CapacidadeExcedidaException("Quantidade de hóspedes maior que a capacidade do quarto.");
        }
    }

    public void validarDisponibilidade(Long quartoId, LocalDate entrada, LocalDate saida) {
        boolean ocupado = aluguelRepository.existsAluguelAtivoNoPeriodo(quartoId, entrada, saida);
        if (ocupado) {
            throw new QuartoIndisponivelException("Quarto indisponível para o período informado.");
        }
    }

    public double calcularValorTotal(Quarto quarto, LocalDate entrada, LocalDate saida) {
        return calcularValorTotal(quarto, entrada, saida, TipoTarifa.PADRAO);
    }

    public double calcularValorTotal(
            Quarto quarto,
            LocalDate entrada,
            LocalDate saida,
            TipoTarifa tipoTarifa) {

        validarDatas(entrada, saida);

        ContextoTarifa contexto = new ContextoTarifa(
                quarto.getValorBase(),
                entrada,
                saida,
                tipoTarifa);

        return GerenciadorTarifas.getInstance().calcularValorTotal(contexto);
    }

    private void validarQuarto(Quarto quarto) {
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto não informado.");
        }
        if (quarto.getTipo() == null) {
            throw new IllegalArgumentException("Tipo de quarto é obrigatório.");
        }
        if (quarto.getValorBase() <= 0) {
            throw new IllegalArgumentException("Valor da diária precisa ser maior que zero.");
        }
    }
}
