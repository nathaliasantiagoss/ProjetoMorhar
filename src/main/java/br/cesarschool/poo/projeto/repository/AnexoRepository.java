package br.cesarschool.poo.projeto.repository;

import br.cesarschool.poo.projeto.model.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    List<Anexo> findByChamadoId(Long chamadoId);
}
