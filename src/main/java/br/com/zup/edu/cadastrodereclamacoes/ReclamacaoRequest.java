package br.com.zup.edu.cadastrodereclamacoes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReclamacaoRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String celular;

    @NotBlank
    @Size(max = 4000)
    private String texto;

    public ReclamacaoRequest(String email, String celular, String texto) {
        this.email = email;
        this.celular = celular;
        this.texto = texto;
    }

    public ReclamacaoRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public String getTexto() {
        return texto;
    }

    public Reclamacao toModel(){
        return new Reclamacao(email, celular, texto);
    }
}
