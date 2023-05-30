package io.github.cursodesousa.msclientes.application;

import io.github.cursodesousa.msclientes.application.representation.ClienteSaveRequest;
import io.github.cursodesousa.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        //Em vez de 'var' pode ser Cliente mesmo. Não entendi bem (ps.: pesquisar sobre)
        var cliente = request.toModel();

        clienteService.clienteSave(cliente);

        //ServletUriComponentsBuilder serve para construir URL's dinâmicas,
        //que são formas de aumentar o URL padrão com informações sobre sobre a operção
        //específicas e seus valores.

        //.fromCurrentRequest() indica para pegar da request desse metodo

        //query() para passar parâmetros via url

        //buildAndExpand(cliente.getCpf() vai "buildar" a url com o cpf passado como parâmetro
        //ele pega o cpf de cliente.getCpf() e joga dentro de cpf indicado por {cpf}
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf = {cpf}").buildAndExpand(cliente.getCpf())
                .toUri(); //transforma em um objeto URI

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam String cpf){
        var cliente = clienteService.getByCpf(cpf);

        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

}
