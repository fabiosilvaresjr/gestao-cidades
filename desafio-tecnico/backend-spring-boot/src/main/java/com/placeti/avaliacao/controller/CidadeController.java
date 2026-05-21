package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter cidades */
//--------------------------------------------------
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cidades")
public class CidadeController {

	private final CidadeService cidadeService;

	public CidadeController(CidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}

	//----------------------------------------------------------
	/** Endpoint que retorna uma cidade conforme seu ID */
	//----------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPeloId(@PathVariable Long id) {
		return ResponseEntity.ok(cidadeService.pesquisarCidade(id));
	}
	
	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	@GetMapping
	public List<CidadeDTO> pesquisarCidades() {
		return cidadeService.pesquisarCidades();
	}

	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades que são capitais */
	//----------------------------------------------------------
	@GetMapping("/capitais")
	public List<CidadeDTO> buscarCapitais() {
		return cidadeService.pesquisarCapitais();
	}

	//----------------------------------------------------------
	/** Endpoint que retorna a cidade por uma parte */
	//----------------------------------------------------------
	@GetMapping("/nome/{nome}")
	public List<CidadeDTO> buscarPorNome(@PathVariable String nome) {
		return cidadeService.buscarPorNome(nome);
	}

	//----------------------------------------------------------
	/** Endpoint que retorna a cidade por UF */
	//----------------------------------------------------------
	@GetMapping("/uf/{uf}")
	public List<CidadeDTO> buscarPorUf(@PathVariable String uf) {
		return cidadeService.buscarPorUf(uf);
	}

	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	@PostMapping
	public void incluirCidade(@Valid @RequestBody CidadeDTO cidadeDto) {
		cidadeService.incluirCidade(cidadeDto);
	}	
	
	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	@PutMapping
	public void alterarCidade(@Valid @RequestBody CidadeDTO cidadeDto) {
		cidadeService.alterarCidade(cidadeDto);
	}
	
	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	@DeleteMapping("/{idCidade}")
	public void excluirCidade(@PathVariable Long idCidade) {
		cidadeService.excluirCidade(idCidade);
	}	
}
