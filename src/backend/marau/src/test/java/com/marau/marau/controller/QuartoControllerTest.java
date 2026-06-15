package com.marau.marau.controller;

import com.marau.marau.enums.TipoQuarto;
import com.marau.marau.model.Quarto;
import com.marau.marau.repository.QuartoRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuartoControllerTest {

    @Test
    void deveListarTodosOsQuartosQuandoTipoNaoForInformado() {

        RepositorioQuartoFake repository = new RepositorioQuartoFake();
        QuartoController controller = new QuartoController(repository.criarProxy(), null);
        List<Quarto> quartosEsperados = List.of(
                criarQuarto(TipoQuarto.INDIVIDUAL),
                criarQuarto(TipoQuarto.FAMILIA));

        repository.todosOsQuartos = quartosEsperados;

        List<Quarto> quartos = controller.listar(null);

        assertEquals(quartosEsperados, quartos);
        assertEquals(1, repository.chamadasFindAll);
        assertEquals(0, repository.chamadasFindByTipo);
    }

    @Test
    void deveListarQuartosFiltradosQuandoTipoForInformado() {

        RepositorioQuartoFake repository = new RepositorioQuartoFake();
        QuartoController controller = new QuartoController(repository.criarProxy(), null);
        List<Quarto> quartosEsperados = List.of(
                criarQuarto(TipoQuarto.FAMILIA));

        repository.quartosFiltrados = quartosEsperados;

        List<Quarto> quartos = controller.listar(TipoQuarto.FAMILIA);

        assertEquals(quartosEsperados, quartos);
        assertEquals(0, repository.chamadasFindAll);
        assertEquals(1, repository.chamadasFindByTipo);
        assertEquals(TipoQuarto.FAMILIA, repository.tipoConsultado);
    }

    private Quarto criarQuarto(TipoQuarto tipo) {

        Quarto quarto = new Quarto();
        quarto.setTipo(tipo);
        return quarto;
    }

    private static class RepositorioQuartoFake implements InvocationHandler {

        private List<Quarto> todosOsQuartos = List.of();
        private List<Quarto> quartosFiltrados = List.of();
        private int chamadasFindAll;
        private int chamadasFindByTipo;
        private TipoQuarto tipoConsultado;

        private QuartoRepository criarProxy() {

            return (QuartoRepository) Proxy.newProxyInstance(
                    QuartoRepository.class.getClassLoader(),
                    new Class<?>[] {QuartoRepository.class},
                    this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {

            if ("findAll".equals(method.getName())
                    && method.getParameterCount() == 0) {

                chamadasFindAll++;
                return todosOsQuartos;
            }

            if ("findByTipo".equals(method.getName())) {

                chamadasFindByTipo++;
                tipoConsultado = (TipoQuarto) args[0];
                return quartosFiltrados;
            }

            if ("toString".equals(method.getName())) {
                return "RepositorioQuartoFake";
            }

            if ("hashCode".equals(method.getName())) {
                return System.identityHashCode(proxy);
            }

            if ("equals".equals(method.getName())) {
                return proxy == args[0];
            }

            throw new UnsupportedOperationException(method.getName());
        }
    }
}
