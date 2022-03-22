package br.com.api.service;

import br.com.api.ApiApplication;
import br.com.api.model.Filme;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@Transactional
public class FilmeTest {


    @Autowired
    FilmeService filmeService;

    @Autowired
    DatabaseLoader databaseLoader;

    private List<Filme> listaFilmesDTO = new ArrayList<>();

    @After
    public void init() throws Exception {
        databaseLoader.run(null);
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

}

