package br.com.b2w.marcioalex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.b2w.marcioalex.dominio.Planeta;
import br.com.b2w.marcioalex.servico.PlanetaService;

@SpringBootTest
class DesafioB2wApplicationTests {
	@Autowired
	private PlanetaService planetaService;

	@Test
	void contextLoads() {
		Properties props = new Properties();
        FileInputStream file;
		try {
			file = new FileInputStream("./src/main/resources/application.properties");
			props.load(file);
			final String API = props.getProperty("api.planetas");
		} catch (IOException e) {
			assertNull(e);
		}
	}
	
	@Test
	void gravacaoPlaneta() {
		Planeta planeta = new Planeta("Teste 1", "Clima 1", "Terreno 1", 20);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaNovo);
		assertEquals(planetaNovo.getQtdFilmes(), 20);
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	void buscaPorId() {
		Planeta planeta = new Planeta("Teste 2", "Clima 2", "Terreno 2", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorID(planetaNovo.getId()));
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	void deleterPlaneta() {
		Planeta planeta = new Planeta("Teste 3", "Clima 3", "Terreno 3", 3);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		if (planetaNovo !=null ) {
			planetaService.removerPlaneta(planetaNovo);
			assertNull(planetaService.buscarPorID(planetaNovo.getId()));
		}
	}

}

