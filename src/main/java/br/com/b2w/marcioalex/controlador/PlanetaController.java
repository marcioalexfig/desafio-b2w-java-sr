package br.com.b2w.marcioalex.controlador;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.b2w.marcioalex.dominio.Planeta;
import br.com.b2w.marcioalex.dto.PlanetResultDTO;
import br.com.b2w.marcioalex.dto.PlanetaDTO;
import br.com.b2w.marcioalex.excecao.StandardError;
import br.com.b2w.marcioalex.servico.PlanetaService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/planetas")
public class PlanetaController {
	private Logger log = Logger.getLogger("PlanetaController");
	
	@Autowired
	private PlanetaService planetaService;

	@Value("${api.planetas}")
	public String url;
	
	@PostMapping("/")
	public ResponseEntity<PlanetaDTO> gravar(@RequestBody PlanetaDTO planetaDto) {
		
		WebClient webclient =
				WebClient.builder()
				.baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		
		Mono<PlanetResultDTO> monoPlaneta = webclient
		.method(HttpMethod.GET)
		.uri("/?search="+planetaDto.getNome())
		.retrieve()
		.bodyToMono(PlanetResultDTO.class);
		
		PlanetResultDTO planResult = monoPlaneta.block();
		
		int qtdFilmes = 0;
		if ( planResult!=null && planResult.getResults()!=null && planResult.getResults().get(0) !=null && planResult.getResults().get(0).getFilms() !=null && planResult.getResults().get(0).getFilms().size()>0 ) {
			qtdFilmes = planResult.getResults().get(0).getFilms().size();
		}
		
		Planeta planeta = new Planeta(planetaDto.getNome(), planetaDto.getClima(), planetaDto.getTerreno(), qtdFilmes);
		Planeta planetaNovo = planetaService.gravarPlaneta(planeta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(planetaNovo.getId()).toUri();
		PlanetaDTO resultado = new PlanetaDTO(planetaNovo.getNome(),
				planetaNovo.getTerreno(), 
				planetaNovo.getClima(),
				planetaNovo.getQtdFilmes(),
				uri.toString());
		return ResponseEntity.created(uri).body(resultado);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable String id) {
		
		if ( id == null ) return ResponseEntity.badRequest().build();
		
		Planeta planeta = planetaService.buscarPorID(id);
		
		if ( planeta == null ) {
			StandardError erro = new StandardError(String.valueOf(System.currentTimeMillis()), 
					String.valueOf(HttpStatus.NOT_FOUND.value()), 
					"Não Encontrado", "","", "");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		}
		
		try {
			planetaService.removerPlaneta( planeta );
		}catch(IllegalArgumentException e) {
			log.info(e.getStackTrace().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/")
	public ResponseEntity<?> buscar(			
			@RequestParam(value="nome", required=false) String nome
			) {
		
		if ( nome != null ) {
			List<Planeta> planetas = planetaService.buscarPorNome(nome);
			Planeta planeta = (planetas != null) ? planetas.get(0) : null;
			
			if ( planeta == null ) {
				StandardError erro = new StandardError(String.valueOf(System.currentTimeMillis()), 
						String.valueOf(HttpStatus.NOT_FOUND.value()), 
						"Não Encontrado", "","", "");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
			}
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replaceQuery("").path("/{id}").buildAndExpand(planeta.getId()).toUri();
			PlanetaDTO planetaDto = new PlanetaDTO(
			planeta.getNome(), 
			planeta.getTerreno(),
			planeta.getClima(), 
			planeta.getQtdFilmes(), uri.toString());
			return ResponseEntity.ok(planetaDto);
		}
		
		List<PlanetaDTO> planetasDto = new ArrayList<>();
		List<Planeta> planetas = planetaService.listarPlanetas();
		if (planetas != null && !planetas.isEmpty()) {
			planetas.stream().forEach( planeta -> {
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replaceQuery("").path("/{id}").buildAndExpand(planeta.getId()).toUri();
				PlanetaDTO planetaDto = new PlanetaDTO(
						planeta.getNome(), 
						planeta.getTerreno(),
						planeta.getClima(), 
						planeta.getQtdFilmes(), uri.toString());
				planetasDto.add(planetaDto);
			});
		}
		
		return ResponseEntity.ok( planetasDto );
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorid(@PathVariable String id) {
		
		if ( id == null ) return ResponseEntity.badRequest().build();
		
		Planeta planeta = planetaService.buscarPorID(id);
		
		if ( planeta == null ) {
				StandardError erro = new StandardError(String.valueOf(System.currentTimeMillis()), 
				String.valueOf(HttpStatus.NOT_FOUND.value()), 
				"Não Encontrado", "",	"", "");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		}
		

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("").buildAndExpand(planeta.getId()).toUri();
		PlanetaDTO planetaDto = new PlanetaDTO(
			planeta.getNome(), 
			planeta.getTerreno(),
			planeta.getClima(),
			planeta.getQtdFilmes(), uri.toString());
		return ResponseEntity.ok(planetaDto);

	}
}
