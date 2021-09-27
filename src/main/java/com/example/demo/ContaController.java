package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ContaController {

    private ContaRepository contaRepository;

    @Autowired
    public ContaController(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @PostMapping("/conta")
    public ResponseEntity abrirConta(@RequestBody String cpf) {

        try {
            var contaParaCriar = new Conta(cpf);
            Conta contaCriada = contaRepository.save(contaParaCriar);
            return ResponseEntity.created(null).body(new ContaAbertaProjection(contaCriada.getNumeroConta()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.unprocessableEntity().body(exception.getMessage());
        }

    }

    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity buscarConta(@PathVariable Long numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta).map(contaProjection -> ResponseEntity.ok(contaProjection)).orElse(ResponseEntity.notFound().build());
    }

}
