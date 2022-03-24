package br.com.api.service;

import br.com.api.model.Filme;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FilmeTest {

    public static String ENDPOINT = "/api/filmes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FilmeService filmeService;

    @Autowired
    DatabaseLoader databaseLoader;

    private List<Filme> listaFilmesDTO = new ArrayList<>();

    @BeforeEach
    public void init() throws Exception {
        listaFilmesDTO = databaseLoader.readCsvFile();
    }

    @Test
    public void testTamanhoArquivoImportado() {
        List<Filme> listaFilmes = filmeService.buscarTodos();
        Assertions.assertThat(listaFilmes.size()).isEqualTo(listaFilmes.size());
    }

    @Test
    public void testDadosImportados() {
        List<Filme> listaFilmes = filmeService.buscarTodos();
        int index = 0;
        if(listaFilmes != null && listaFilmes.size() > 0)
            index = listaFilmes.size() - 1;

        Assertions.assertThat(listaFilmes.get(index).getProducers()).isEqualTo(listaFilmesDTO.get(index).getProducers());
        Assertions.assertThat(listaFilmes.get(index).getYear()).isEqualTo(listaFilmesDTO.get(index).getYear());
    }

    @Test
    void testEndPoint() throws Exception {
        mockMvc.perform(get(ENDPOINT)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}

