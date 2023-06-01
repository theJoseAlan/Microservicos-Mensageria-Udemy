package io.github.cursodesousa.mscartoes.application;

import io.github.cursodesousa.mscartoes.application.representation.CartaoSaveRequest;
import io.github.cursodesousa.mscartoes.domain.Cartao;
import io.github.cursodesousa.mscartoes.infra.repository.CartaoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
@AllArgsConstructor
public class CartoesResource {

    private CartaoService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){

        Cartao cartao = request.toModel();

        service.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
