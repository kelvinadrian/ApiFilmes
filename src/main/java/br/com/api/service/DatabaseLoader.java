package br.com.api.service;

import br.com.api.model.Filme;
import br.com.api.model.FilmeDTO;
import br.com.api.repository.FilmeRepository;
import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    public static final String CSV_FILE = "movielist.csv";

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public void run(String... args) throws Exception {
        filmeRepository.saveAll(readCsvFile());
    }

    public List<Filme> readCsvFile() throws IOException {
        List<String[]> lista = readCsvFileByString();
        return montarListaFilmes(lista);
    }

    public List<String[]> readCsvFileByString() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));

        final CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();

        return csvReader.readAll();
    }

    public List<Filme> montarListaFilmes(List<String[]> filmes){
        List<Filme> listaFilmes = new ArrayList<>();
        for (String[] obj : filmes) {
            Filme filme = new Filme();
            filme.setYear(Integer.parseInt(obj[0]));
            filme.setTitle(obj[1]);
            filme.setStudios(obj[2]);
            filme.setProducers(obj[3]);
            filme.setWinner(Boolean.FALSE);
            if(obj[4] != null && "yes".equalsIgnoreCase(obj[4])){
                filme.setWinner(Boolean.TRUE);
            }
            listaFilmes.add(filme);
        }
        return listaFilmes;
    }
}
