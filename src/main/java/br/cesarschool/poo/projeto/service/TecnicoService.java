package br.cesarschool.poo.projeto.service;

import br.cesarschool.poo.projeto.model.Tecnico;
import br.cesarschool.poo.projeto.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    public List<Tecnico> listarTodos() {
        return repository.findAll();
    }

    public List<Tecnico> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Tecnico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Tecnico salvar(Tecnico tecnico) {
        return repository.save(tecnico);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
