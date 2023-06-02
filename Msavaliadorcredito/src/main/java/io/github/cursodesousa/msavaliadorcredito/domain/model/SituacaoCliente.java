package io.github.cursodesousa.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //facilita a criação de um objeto dessa classe
public class SituacaoCliente {

    private DadosCliente cliente;
    private List<CartaoCliente> cartoes;

}
