package br.cesarschool.poo.projeto.service;

import br.cesarschool.poo.projeto.model.Agendamento;
import br.cesarschool.poo.projeto.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired private AgendaRepository repository;

    public List<Agendamento> listarTodos() { return repository.findAll(); }
    public Optional<Agendamento> buscarPorId(Long id) { return repository.findById(id); }
    public List<Agendamento> buscarPorStatus(String status) { return repository.findByStatus(status); }
    public List<Agendamento> buscarPorData(String data) { return repository.findByDataAgendamento(data); }
    public List<Agendamento> buscarPorTecnico(String tecnico) { return repository.findByTecnico(tecnico); }
    public Agendamento salvar(Agendamento a) { return repository.save(a); }
    public void excluir(Long id) { repository.deleteById(id); }
    public List<Agendamento> buscarPorChamado(Long chamadoId) { return repository.findByChamadoId(chamadoId); }
    public boolean temAgendamentoAtivo(Long chamadoId, Long excluirId) {
        return repository.findByChamadoIdAndStatusIn(chamadoId, List.of("Agendado", "Confirmado"))
                .stream().anyMatch(a -> !a.getId().equals(excluirId));
    }
}
