package br.cesarschool.poo.projeto.repository;

import br.cesarschool.poo.projeto.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByStatus(String status);
}
