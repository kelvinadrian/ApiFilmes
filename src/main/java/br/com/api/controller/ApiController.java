package br.com.api.controller;

import br.com.api.model.ResponseListDTO;
import br.com.api.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/filmes")
public class ApiController {

	@Autowired
	private FilmeService filmeService;

	@GetMapping
	public ResponseEntity<ResponseListDTO> consultarVencedores() {
		return ResponseEntity.ok().body(filmeService.consultarVencedoresComIntervaloMinMax());
	}
	
}
