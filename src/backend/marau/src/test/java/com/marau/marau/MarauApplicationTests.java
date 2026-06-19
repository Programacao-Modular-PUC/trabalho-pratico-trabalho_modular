package com.marau.marau;

import com.marau.marau.repository.AluguelRepository;
import com.marau.marau.repository.ClienteRepository;
import com.marau.marau.repository.ImovelRepository;
import com.marau.marau.repository.QuartoRepository;
import com.marau.marau.repository.ReservaRepository;
import com.marau.marau.repository.ResidenciaRepository;
import com.marau.marau.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude="
				+ "org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration,"
				+ "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration,"
				+ "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration"
})
class MarauApplicationTests {

	@Test
	void contextLoads() {
	}

	@TestConfiguration
	static class RepositoryTestConfiguration {

		@Bean
		AluguelRepository aluguelRepository() {
			return criarRepositorio(AluguelRepository.class);
		}

		@Bean
		ClienteRepository clienteRepository() {
			return criarRepositorio(ClienteRepository.class);
		}

		@Bean
		QuartoRepository quartoRepository() {
			return criarRepositorio(QuartoRepository.class);
		}

		@Bean
		ResidenciaRepository residenciaRepository() {
			return criarRepositorio(ResidenciaRepository.class);
		}

		@Bean
		ImovelRepository imovelRepository() {
			return criarRepositorio(ImovelRepository.class);
		}

		@Bean
		ReservaRepository reservaRepository() {
			return criarRepositorio(ReservaRepository.class);
		}

		@Bean
		UsuarioRepository usuarioRepository() {
			return criarRepositorio(UsuarioRepository.class);
		}

		private <T> T criarRepositorio(Class<T> tipoRepositorio) {

			return tipoRepositorio.cast(
					Proxy.newProxyInstance(
							tipoRepositorio.getClassLoader(),
							new Class<?>[] {tipoRepositorio},
							new RepositorioSemBanco(tipoRepositorio)));
		}
	}

	private static class RepositorioSemBanco implements InvocationHandler {

		private final Class<?> tipoRepositorio;

		private RepositorioSemBanco(Class<?> tipoRepositorio) {
			this.tipoRepositorio = tipoRepositorio;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {

			if ("findAll".equals(method.getName())
					&& method.getParameterCount() == 0) {
				return List.of();
			}

			if ("findById".equals(method.getName())) {
				return Optional.empty();
			}

			if ("save".equals(method.getName())) {
				return args[0];
			}

			if ("toString".equals(method.getName())) {
				return tipoRepositorio.getSimpleName() + "SemBanco";
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
