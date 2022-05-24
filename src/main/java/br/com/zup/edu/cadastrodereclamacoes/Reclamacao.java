package br.com.zup.edu.cadastrodereclamacoes;

import javax.persistence.*;
import java.time.LocalDate;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "Unique_reclamacao_celular_texto"
                , columnNames = {"celular", "texto"})
})
@Entity
public class Reclamacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false, length = 64)
    private String hashCelular;

    @Column(nullable = false, length = 4000)
    private String texto;

    @Column(nullable = false)
    private LocalDate dataRegistro = LocalDate.now();

    public Reclamacao(String email, String celular, String texto) {
        this.email = email;
        this.celular = CelularUtils.anonymize(celular);
        this.hashCelular = CelularUtils.hash(celular);
        this.texto = texto;
    }

    @Deprecated
    public Reclamacao() {
    }

    public Long getId() {
        return id;
    }
}


