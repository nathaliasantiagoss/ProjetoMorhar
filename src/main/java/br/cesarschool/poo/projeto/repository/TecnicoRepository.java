package br.cesarschool.poo.projeto.repository;

import br.cesarschool.poo.projeto.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    List<Tecnico> findByNomeContainingIgnoreCase(String nome);
}
