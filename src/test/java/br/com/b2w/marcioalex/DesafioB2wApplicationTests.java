package br.com.b2w.marcioalex;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.b2w.marcioalex.dominio.Planeta;
import br.com.b2w.marcioalex.servico.PlanetaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest
class DesafioB2wApplicationTests {
	@Autowired
	private PlanetaService planetaService;

	@BeforeAll
	static void urlBase() {
		RestAssured.baseURI = "http://localhost:8080/";
	}
	@Test
	@DisplayName("Arquivo 'application.properties'")
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
	@DisplayName("Banco: Gravar")
	void gravacaoPlaneta() {
		Planeta planeta = new Planeta("Teste 1", "Clima 1", "Terreno 1", 20);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaNovo);
		assertEquals(planetaNovo.getQtdFilmes(), 20);
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("Banco: Buscar por Nome")
	void buscaPorNome() {
		Planeta planeta = new Planeta("Teste 3", "Clima 3", "Terreno 3", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorNome("Teste 3"));
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("Banco: Buscar por Id")
	void buscaPorId() {
		Planeta planeta = new Planeta("Teste 2", "Clima 2", "Terreno 2", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorID(planetaNovo.getId()));
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("Banco: Listar")
	void listar() {
		Planeta planeta = new Planeta("Teste Lista", "Clima Lista", "Terreno Lista", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorID(planetaNovo.getId()));
		List<Planeta> lista = planetaService.listarPlanetas();
		assertThat(lista.size() > 0);
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("Banco: Deletar")
	void deleterPlaneta() {
		Planeta planeta = new Planeta("Teste 3", "Clima 3", "Terreno 3", 3);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		if (planetaNovo !=null ) {
			planetaService.removerPlaneta(planetaNovo);
			assertNull(planetaService.buscarPorID(planetaNovo.getId()));
		}
	}
	
	@Test
	@DisplayName("API: Gravar")
	void gravarPlanetaApi() {
		String url = "planetas/";
		String body = "{\"nome\":\"Alderaan\",\"clima\":\"Glacial\",\"terreno\":\"Montanhoso\"}";
		given()
		.contentType(ContentType.JSON)
		.body(body)
		.when()
		        .post(url)
		.then()
		        .statusCode(201);	
	}
	
	@Test
	@DisplayName("API: Listar")
	void listarPlanetaApi() {
		String url = "planetas/";
		given()
		.when()
		        .get(url)
		.then()
		        .statusCode(200);	
	}
	
	@Test
	@DisplayName("API: Busca por ID")
	void buscarPorIdApi() {
		Planeta planeta = new Planeta("Teste ID", "Clima ID", "Terreno ID", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorID(planetaNovo.getId()));
		
		List<Planeta> lista = planetaService.listarPlanetas();
		assertThat(lista.size() > 0);
		
		if( planetaNovo!=null && planetaNovo.getId()!=null) {
			String url = "planetas/" + planetaNovo.getId();
			given()
			.when()
			        .get(url)
			.then()
			        .statusCode(200);	
		}
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("API: Busca por Nome")
	void buscarPorNomeApi() {
		Planeta planeta = new Planeta("Teste Nome", "Clima Nome", "Terreno Nome", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		
		if( planetaNovo!=null && planetaNovo.getId()!=null) {
			String url = "planetas/";
			given()
					.param("nome", planetaNovo.getNome())
			.when()
			    	.get(url)
			.then()
			        .statusCode(200);	
		}
		planetaService.removerPlaneta(planetaNovo);
	}
	
	@Test
	@DisplayName("API: Deletar")
	void deletarApi() {
		Planeta planeta = new Planeta("Teste ID", "Clima ID", "Terreno ID", 1);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		assertNotNull(planetaService.buscarPorID(planetaNovo.getId()));

		if( planetaNovo!=null && planetaNovo.getId()!=null) {
			String url = "planetas/" + planetaNovo.getId();
			when()
			        .delete(url)
			.then()
			        .statusCode(204);	
		}

		assertNull(planetaService.buscarPorID(planetaNovo.getId()));
	
	}
}

