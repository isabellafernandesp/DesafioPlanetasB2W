package com.desafioplanetas.api.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafioplanetas.api.models.Planeta;
import com.desafioplanetas.api.responses.Response;
import com.desafioplanetas.api.services.PlanetaService;

@RestController
@RequestMapping(path = "api/planetas")
public class PlanetaController {
	
	@Autowired
	private PlanetaService planetaService;
	
	@GetMapping
	public ResponseEntity<Response<List<Planeta>>> listarPlanetas() {
		return ResponseEntity.ok(new Response<List<Planeta>>(this.planetaService.listarPlanetas()));
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<Planeta>> buscarPorId(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<Planeta>(this.planetaService.buscarPorId(id)));
	}
	
	@GetMapping(path = "/nome/{nome}")
	public ResponseEntity<Response<Planeta>> buscarPorNome(@PathVariable(name = "nome") String nome) {
		return ResponseEntity.ok(new Response<Planeta>(this.planetaService.buscarPorNome(nome)));
	}
	
	@PostMapping
	public ResponseEntity<Response<Planeta>> adicionarPlaneta(@Valid @RequestBody Planeta planeta, BindingResult result) {
		if(result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Planeta>(erros));
		}
		
		return ResponseEntity.ok(new Response<Planeta>(this.planetaService.adicionarPlaneta(planeta)));
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Integer>> removerPlaneta(@PathVariable(name = "id") String id) {
		this.planetaService.removerPlaneta(id);
		
		return ResponseEntity.ok(new Response<Integer>(1));
	}
}
