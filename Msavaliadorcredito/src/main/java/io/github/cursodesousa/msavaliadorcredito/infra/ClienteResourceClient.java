package io.github.cursodesousa.msavaliadorcredito.infra;

import io.github.cursodesousa.msavaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//Client da Api de Clientes
//value -> pega o valor no application.yml em spring application name
//path -> nome que está na classe controller (ou resources) dentro de @RequestMapping
@FeignClient(value = "msclientes", path = "/clientes") //É um cliente HTTP, responsável por faze a conexão entre as API's (microsserviços)
public interface ClienteResourceClient {


    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam String cpf);


}
