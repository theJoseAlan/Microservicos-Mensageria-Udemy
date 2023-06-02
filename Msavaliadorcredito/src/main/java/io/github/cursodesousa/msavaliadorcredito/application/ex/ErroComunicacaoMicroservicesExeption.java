package io.github.cursodesousa.msavaliadorcredito.application.ex;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

public class ErroComunicacaoMicroservicesExeption extends Exception{

    @Getter
    private Integer status;

    public ErroComunicacaoMicroservicesExeption(String msg, Integer status) {
        super(msg);

        this.status = status;

    }
}
