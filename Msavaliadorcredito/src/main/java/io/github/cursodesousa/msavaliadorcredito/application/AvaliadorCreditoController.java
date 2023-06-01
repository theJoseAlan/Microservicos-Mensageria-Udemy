package io.github.cursodesousa.msavaliadorcredito.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-credito")
public class AvaliadorCreditoController {

    @GetMapping
    public String status(){
        return "ok";
    }


}
