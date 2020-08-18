package br.com.b2w.marcioalex.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b2w.marcioalex.dominio.Planeta;
import br.com.b2w.marcioalex.repositorio.PlanetaRepository;

@Service
public class PlanetaService {
	
	@Autowired
	PlanetaRepository planetaRepository;
	
	public List<Planeta> listarPlanetas(){
		return planetaRepository.findAll();
	}
	
	public Planeta buscarPorNome(String nome) {
		return planetaRepository.findByNomeContains(nome);
	}
	
	public Planeta buscarPorID(String id) {
		return planetaRepository.findById(id);
	}
	
	public void removerPlaneta(Planeta planeta) {
		planetaRepository.delete( planeta );
	}
	
	public Planeta gravarPlaneta(Planeta planeta) {
		return planetaRepository.insert( planeta );
	}
}
