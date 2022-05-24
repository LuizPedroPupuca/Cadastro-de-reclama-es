package br.com.zup.edu.cadastrodereclamacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {

    boolean existsByHashCelularAndTexto(String hashCelular, String texto);
}
