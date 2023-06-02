package io.github.cursodesousa.msavaliadorcredito.application;

import feign.FeignException;
import io.github.cursodesousa.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.cursodesousa.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesExeption;
import io.github.cursodesousa.msavaliadorcredito.domain.model.*;
import io.github.cursodesousa.msavaliadorcredito.infra.CartoesResourceClient;
import io.github.cursodesousa.msavaliadorcredito.infra.ClienteResourceClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExeption{

        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = clientsResourceClient.dadosCliente(cpf);

            //Retorna os cartoes disponiveis para a renda do cliente
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();

                BigDecimal idadeBd = BigDecimal.valueOf(dadosCliente.getIdade());

                var fator =idadeBd.divide(BigDecimal.valueOf(10));

                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setNome(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
                    }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status = e.status();

            if(HttpStatus.NOT_FOUND.value()==status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesExeption(e.getMessage(), status);
        }

    }

}
