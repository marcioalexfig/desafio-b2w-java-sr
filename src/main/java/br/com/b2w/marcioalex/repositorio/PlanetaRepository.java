package br.com.b2w.marcioalex.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.b2w.marcioalex.dominio.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, Integer> {
	List<Planeta> findByNomeContains(String nome);
	Planeta findById(String id);
}
