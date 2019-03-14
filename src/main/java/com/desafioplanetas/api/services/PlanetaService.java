package com.desafioplanetas.api.services;

import java.util.List;

import com.desafioplanetas.api.models.Planeta;

public interface PlanetaService {

	List<Planeta> listarPlanetas();
	
	Planeta adicionarPlaneta(Planeta planeta);
	
	Planeta buscarPorNome(String nome);
	
	Planeta buscarPorId(String id);
	
	void removerPlaneta(String id);
	
}
