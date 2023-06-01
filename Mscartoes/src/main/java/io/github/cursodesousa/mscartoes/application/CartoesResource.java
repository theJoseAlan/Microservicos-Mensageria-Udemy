package io.github.cursodesousa.mscartoes.application;

import io.github.cursodesousa.mscartoes.application.representation.CartaoSaveRequest;
import io.github.cursodesousa.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.cursodesousa.mscartoes.domain.Cartao;
import io.github.cursodesousa.mscartoes.domain.ClienteCartao;
import io.github.cursodesousa.mscartoes.infra.repository.CartaoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@AllArgsConstructor
public class CartoesResource {

    private CartaoService service;

    private final ClienteCartaoService clienteCartaoService;

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

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> list = service.getCartoesRendaMenorIgual(renda);

        return ResponseEntity.ok(list);

    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){

        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);

        List<CartoesPorClienteResponse> resultlist = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultlist);

    }

}
