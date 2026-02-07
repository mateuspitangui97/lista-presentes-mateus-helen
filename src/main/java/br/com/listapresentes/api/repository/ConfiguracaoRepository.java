package br.com.listapresentes.api.repository;

import br.com.listapresentes.api.entity.ConfiguracaoLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<ConfiguracaoLista, Long> {

    Optional<ConfiguracaoLista> findFirstByAtivoTrue();
}
