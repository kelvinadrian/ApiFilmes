package br.com.api.service;


import br.com.api.model.Filme;
import br.com.api.model.ResponseDTO;
import br.com.api.model.ResponseListDTO;
import br.com.api.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public List<Filme> buscarTodos(){
        return filmeRepository.findAll();
    }

    public ResponseListDTO consultarVencedoresComIntervaloMinMax(){
        Map<String, List<Integer>> grupoProducer = new HashMap<>();
        List<Filme> listaFilme = filmeRepository.findAllWinner();

        for(Filme filme : listaFilme){
            String[] splittedProducers = filme.getProducers().split(",\\s*|\\band\\bs*");
            for(String producer : splittedProducers){
                if(grupoProducer.containsKey(producer.trim())){
                    List<Integer> yearValueList = grupoProducer.get(producer.trim());
                    yearValueList.add(filme.getYear());
                    grupoProducer.put(producer.trim(), yearValueList);
                }else{
                    List<Integer> yearList = new ArrayList<>();
                    yearList.add(filme.getYear());
                    grupoProducer.put(producer.trim(), yearList);
                }

            }
        }

        ResponseListDTO respondeList = new ResponseListDTO();
        respondeList.setMin(montarDados(grupoProducer, false));
        respondeList.setMax(montarDados(grupoProducer, true));

        return respondeList;
    }

    private List<ResponseDTO> montarDados(Map<String, List<Integer>> grupoProducer, Boolean isMax){
        int intervalo = 1;
        List<ResponseDTO> producerDtoList = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : grupoProducer.entrySet()) {
            List<Integer> winnerYears = entry.getValue();
            if(winnerYears != null && winnerYears.size() > 1){
                int currentYear = 0;
                int currentIntervalo = 0;
                for(Integer year : winnerYears){
                    if(currentYear == 0){
                        currentYear = year;
                        continue;
                    }

                    currentIntervalo = year - currentYear;

                    if((isMax && currentIntervalo >= intervalo) || (!isMax && currentIntervalo <= intervalo)){
                        ResponseDTO producerDto = new ResponseDTO();
                        intervalo = currentIntervalo;
                        producerDto.setProducer(entry.getKey());
                        producerDto.setInterval(intervalo);
                        producerDto.setPreviousWin(currentYear);
                        producerDto.setFollowingWin(year);
                        producerDtoList.add(producerDto);
                    }
                    currentYear = year;
                }
                intervalo = 1;
            }
        }

        List<ResponseDTO> producersSorted = new ArrayList<>();
        if(producerDtoList != null && producerDtoList.size() > 0) {
            if(isMax)
                producerDtoList.sort(Comparator.comparingInt(ResponseDTO::getInterval).reversed());
            else
                producerDtoList.sort(Comparator.comparingInt(ResponseDTO::getInterval));
            int first = producerDtoList.get(0).getInterval();
            producersSorted = producerDtoList.stream().filter(p -> p.getInterval() == first).collect(Collectors.toList());
            producersSorted.sort(Comparator.comparingInt(ResponseDTO::getPreviousWin));
        }
        return producersSorted;
    }

}
