package io.github.cursodesousa.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.cms.bc.BcKEKRecipientInfoGenerator;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoAprovado {
    private String nome;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
