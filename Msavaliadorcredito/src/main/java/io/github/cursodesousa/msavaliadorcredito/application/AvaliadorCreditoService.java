package io.github.cursodesousa.msavaliadorcredito.application;

import feign.Client;
import io.github.cursodesousa.msavaliadorcredito.domain.model.DadosCliente;
import io.github.cursodesousa.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.cursodesousa.msavaliadorcredito.infra.ClienteResourceClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientsResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        //Obter dados de msClientes
        //Obter cartoes do cliente de msCartoes

        ResponseEntity<DadosCliente> dadosClienteResponse = clientsResourceClient.dadosCliente(cpf);


        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();


    }

}
