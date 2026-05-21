package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService projetoService;

    @Test
    void devePesquisarCidadePeloId() {
        Cidade cidade = new Cidade();
        cidade.setId(1L);
        cidade.setNome("Brasilia");
        cidade.setUf("DF");
        cidade.setCapital(true);

        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

        CidadeDTO resultado = projetoService.pesquisarCidade(1L);

        assertEquals(1L, resultado.id());
        assertEquals("Brasilia", resultado.nome());
        assertEquals("DF", resultado.uf());
        assertEquals(true, resultado.capital());
    }

    @Test
    void devePesquisarTodasAsCidades() {
        Cidade cidade = new Cidade();
        cidade.setId(1L);
        cidade.setNome("Goiania");
        cidade.setUf("GO");
        cidade.setCapital(true);

        when(cidadeRepository.findAll()).thenReturn(List.of(cidade));

        List<CidadeDTO> resultado = projetoService.pesquisarCidades();

        assertEquals(1, resultado.size());
        assertEquals("Goiania", resultado.get(0).nome());
    }

    @Test
    void deveIncluirCidade() {
        CidadeDTO dto = new CidadeDTO(null, "Florianopolis", "SC", true);

        projetoService.incluirCidade(dto);

        ArgumentCaptor<Cidade> captor = ArgumentCaptor.forClass(Cidade.class);
        verify(cidadeRepository).save(captor.capture());
        assertEquals("Florianopolis", captor.getValue().getNome());
        assertEquals("SC", captor.getValue().getUf());
        assertEquals(true, captor.getValue().getCapital());
    }

    @Test
    void deveAlterarCidade() {
        Cidade cidade = new Cidade();
        cidade.setId(11L);
        cidade.setNome("Cidade Antiga");
        cidade.setUf("SC");
        cidade.setCapital(true);

        when(cidadeRepository.findById(11L)).thenReturn(Optional.of(cidade));

        projetoService.alterarCidade(new CidadeDTO(11L, "Blumenau", "SC", false));

        assertEquals("Blumenau", cidade.getNome());
        assertFalse(cidade.getCapital());
        verify(cidadeRepository).save(cidade);
    }

    @Test
    void deveExcluirCidadeExistente() {
        Cidade cidade = new Cidade();
        cidade.setId(5L);

        when(cidadeRepository.findById(5L)).thenReturn(Optional.of(cidade));

        projetoService.excluirCidade(5L);

        verify(cidadeRepository).delete(cidade);
    }

    @Test
    void deveFalharAoPesquisarCidadeInexistente() {
        when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> projetoService.pesquisarCidade(99L));
    }
}
