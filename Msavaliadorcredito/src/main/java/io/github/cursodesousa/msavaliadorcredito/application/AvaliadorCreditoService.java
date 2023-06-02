package io.github.cursodesousa.msavaliadorcredito.application;

import feign.FeignException;
import io.github.cursodesousa.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.cursodesousa.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesExeption;
import io.github.cursodesousa.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.cursodesousa.msavaliadorcredito.domain.model.DadosCliente;
import io.github.cursodesousa.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.cursodesousa.msavaliadorcredito.infra.CartoesResourceClient;
import io.github.cursodesousa.msavaliadorcredito.infra.ClienteResourceClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientsResourceClient;

    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException,
            ErroComunicacaoMicroservicesExeption{
        //Obter dados de msClientes
        //Obter cartoes do cliente de msCartoes

        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clientsResourceClient.dadosCliente(cpf);

            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);


            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
           int status = e.status();

           if(HttpStatus.NOT_FOUND.value()==status){
               throw new DadosClienteNotFoundException();
           }

           throw new ErroComunicacaoMicroservicesExeption(e.getMessage(), status);
        }




    }

}
