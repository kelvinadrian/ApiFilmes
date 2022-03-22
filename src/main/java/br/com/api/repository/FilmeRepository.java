package br.com.api.repository;

import br.com.api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    @Query("select f from Filme f where winner = true order by year asc")
    List<Filme> findAllWinner();
}
