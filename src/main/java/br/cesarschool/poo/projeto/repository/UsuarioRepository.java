package br.cesarschool.poo.projeto.repository;

import br.cesarschool.poo.projeto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByTecnicoEmail(String email);
}
