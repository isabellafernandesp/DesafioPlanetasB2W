package com.desafioplanetas.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.desafioplanetas.api.models.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	Planeta findByNome(String nome);

}
