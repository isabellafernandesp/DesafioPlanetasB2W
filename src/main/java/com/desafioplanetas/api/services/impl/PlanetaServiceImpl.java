package com.desafioplanetas.api.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.desafioplanetas.api.models.Planeta;
import com.desafioplanetas.api.repositories.PlanetaRepository;
import com.desafioplanetas.api.services.PlanetaService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Override
	public List<Planeta> listarPlanetas() {
		return this.planetaRepository.findAll();
	}

	@Override
	public Planeta adicionarPlaneta(Planeta planeta) {
		planeta.setQtdAparicoesFilmes(quantidadeAparicoesDoPlanetaEmFilmes(planeta.getNome()));
		
		return this.planetaRepository.save(planeta);
	}

	@Override
	public Planeta buscarPorNome(String nome) {
		return this.planetaRepository.findByNome(nome);
	}

	@Override
	public Planeta buscarPorId(String id) {
		return this.planetaRepository.findOne(id);
	}

	@Override
	public void removerPlaneta(String id) {
		this.planetaRepository.delete(id);
	}

	private Integer quantidadeAparicoesDoPlanetaEmFilmes(String nome) {
		StringBuilder url = new StringBuilder();
		url.append("https://swapi.co/api/planets/?search=").append(nome);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		Object object;
		try {
			object = restTemplate.exchange(url.toString(), HttpMethod.GET,
					new HttpEntity<String>("parameters", headers), Object.class);
		} catch (Exception e) {
			return null;
		}

		Gson gson = new Gson();
		JsonArray results = gson.fromJson(gson.toJson(object), JsonObject.class).getAsJsonObject("body")
				.getAsJsonArray("results");

		JsonElement correctResult = null;

		for (JsonElement e : results) {
			if (e.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(nome)) {
				correctResult = e;
			}
		}

		if (correctResult == null) {
			return 0;
		} else {
			return correctResult.getAsJsonObject().getAsJsonArray("films").size();
		}
	}
	
}
