package br.cesarschool.poo.projeto.service;

import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public List<Chamado> listarTodos() {
        return repository.findAll();
    }

    public List<Chamado> buscarPorStatus(String status) {
        return repository.findByStatus(status);
    }

    public Optional<Chamado> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Chamado salvar(Chamado chamado) {
        return repository.save(chamado);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public long contarAbertos() {
        return repository.findByStatus("Aberto").size() + repository.findByStatus("Urgente").size();
    }

    public long contarEmAndamento() {
        return repository.findByStatus("Em andamento").size();
    }

    public long contarFinalizados() {
        return repository.findByStatus("Finalizado").size();
    }
}
