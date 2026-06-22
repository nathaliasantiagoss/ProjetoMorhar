package br.cesarschool.poo.projeto.repository;

import br.cesarschool.poo.projeto.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatus(String status);
    List<Agendamento> findByDataAgendamento(String data);
    List<Agendamento> findByTecnico(String tecnico);
    List<Agendamento> findByChamadoId(Long chamadoId);
    List<Agendamento> findByChamadoIdAndStatusIn(Long chamadoId, List<String> statuses);
}
