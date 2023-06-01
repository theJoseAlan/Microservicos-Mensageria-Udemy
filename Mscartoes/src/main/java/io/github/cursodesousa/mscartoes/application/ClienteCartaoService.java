package io.github.cursodesousa.mscartoes.application;

import io.github.cursodesousa.mscartoes.domain.ClienteCartao;
import io.github.cursodesousa.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteCartaoService {

    private ClienteCartaoRepository clienteCartaoRepository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return clienteCartaoRepository.findByCpf(cpf);
    }



}
