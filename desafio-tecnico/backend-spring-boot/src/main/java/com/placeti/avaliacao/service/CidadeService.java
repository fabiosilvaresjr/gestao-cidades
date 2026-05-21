package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

//------------------------------------------------------------------
/** Service usado para acessar os repositórios da aplicação */
//------------------------------------------------------------------
@Service
public class CidadeService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CidadeRepository cidadeRepository;

	public CidadeService(CidadeRepository cidadeRepository) {
		this.cidadeRepository = cidadeRepository;
	}

	//---------------------------------------------------------

	/**
	 * Método que busca uma cidade pelo seu ID
	 */
	//---------------------------------------------------------
	public CidadeDTO pesquisarCidade(Long id) {
		logger.info("Pesquisando cidade pelo id {}", id);
		System.out.println("Buscando cidades...");
		return cidadeRepository.findById(id)
				.map(cidade -> new CidadeDTO(
						cidade.getId(),
						cidade.getNome(),
						cidade.getUf(),
						cidade.getCapital()
				))
				.orElseThrow(() -> new IllegalArgumentException("Cidade nao encontrada para o id " + id));
	}

	//---------------------------------------------------------

	/**
	 * Método que retorna todas as cidades cadastradas
	 */
	//---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {
		logger.info("Pesquisando todas as cidades");

		return cidadeRepository.findAll().stream()
				.map(cidade -> new CidadeDTO(
						cidade.getId(),
						cidade.getNome(),
						cidade.getUf(),
						cidade.getCapital()
				))
				.collect(java.util.stream.Collectors.toList());
	}

	//----------------------------------------------------------


	//----------------------------------------------------------
	/** Método chamado para filtrar apenas cidades com capitais */
	//----------------------------------------------------------

	public List<CidadeDTO> pesquisarCapitais() {
		logger.info("Pesquisando cidades que são capitais");

		return cidadeRepository.findAll().stream()
				.filter(c -> c.getCapital())
				.map(this::converterParaDTO)
				.collect(java.util.stream.Collectors.toList());
	}

	//----------------------------------------------------------
	/** Método chamado para filtrar por nome e parte do nome */
	//----------------------------------------------------------
	public List<CidadeDTO> buscarPorNome(String nome) {
		logger.info("Buscando cidades pelo nome: {}", nome);

		return cidadeRepository.findAll().stream()
				.filter(cidade -> cidade.getNome().toLowerCase().contains(nome.toLowerCase()))
				.map(this::converterParaDTO)
				.collect(java.util.stream.Collectors.toList());
	}

	//----------------------------------------------------------
	/** Método chamado para filtrar por UF */
	//----------------------------------------------------------
	public List<CidadeDTO> buscarPorUf(String uf) {
		logger.info("Buscando cidades pela UF: {}", uf);

		return cidadeRepository.findAll().stream()
				.filter(cidade -> cidade.getUf().equalsIgnoreCase(uf))
				.map(this::converterParaDTO)
				.collect(java.util.stream.Collectors.toList());
	}

	/**
	 * Método chamado para incluir uma nova cidade
	 */
	//----------------------------------------------------------
	public void incluirCidade(CidadeDTO dto) {
		logger.info("Incluindo cidade {}", dto.nome());
		Cidade cidade = dto.toEntity();
		cidade.setId(null);
		cidadeRepository.save(cidade);
	}

	//----------------------------------------------------------

	/**
	 * Método chamado para alterar os dados de uma cidade
	 */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {
		logger.info("Alterando cidade {}", dto.id());
		if (dto.id() == null) {
			throw new IllegalArgumentException("Id da cidade deve ser informado para alteracao");
		}
		Cidade cidade = cidadeRepository.findById(dto.id())
				.orElseThrow(() -> new IllegalArgumentException("Cidade nao encontrada para o id " + dto.id()));
		cidade.setNome(dto.nome());
		cidade.setUf(dto.uf());
		cidade.setCapital(dto.capital());
		cidadeRepository.save(cidade);
	}

	//----------------------------------------------------------

	/**
	 * Método chamado para excluir uma cidade
	 */
	//----------------------------------------------------------
	public void excluirCidade(Long idCidade) {
		logger.info("Excluindo cidade {}", idCidade);
		Cidade cidade = cidadeRepository.findById(idCidade)
				.orElseThrow(() -> new IllegalArgumentException("Cidade nao encontrada para o id " + idCidade));
		cidadeRepository.delete(cidade);
	}

	private CidadeDTO converterParaDTO(Cidade cidade) {
		return new CidadeDTO(
				cidade.getId(),
				cidade.getNome(),
				cidade.getUf(),
				cidade.getCapital()
		);
	}

}