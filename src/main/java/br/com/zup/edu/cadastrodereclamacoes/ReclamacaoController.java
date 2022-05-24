package br.com.zup.edu.cadastrodereclamacoes;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class ReclamacaoController {

    @Autowired
    private ReclamacaoRepository reclamacaoRepository;
    @PostMapping("/reclamacao")
    public ResponseEntity<?> cadastra(@RequestBody @Valid ReclamacaoRequest reclamacaoRequest,
                                      UriComponentsBuilder uriComponentsBuilder){
        String hashCelular = CelularUtils.hash(reclamacaoRequest.getCelular());
        if(reclamacaoRepository.existsByHashCelularAndTexto(hashCelular, reclamacaoRequest.getTexto())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Celular e reclamação já existentes");
        }

        Reclamacao reclamacao = reclamacaoRequest.toModel();
        reclamacaoRepository.save(reclamacao);

        URI location = uriComponentsBuilder.path("/reclamacao/{id}")
                .buildAndExpand(reclamacao.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> errorUniqueException(ConstraintViolationException e, WebRequest request){
        Map<String, Object> body = Map.of(
                "status", 422,
                "error", "UNPROCESSABLE_ENTITY",
                "timestamp", LocalDateTime.now(),
                "path", request.getDescription(false).replace("uri=",""),
                "message", "Celular e texto já existentes"
        );

        return ResponseEntity.unprocessableEntity().body(body);
    }
}
