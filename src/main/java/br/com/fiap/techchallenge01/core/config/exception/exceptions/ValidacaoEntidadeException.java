package br.com.fiap.techchallenge01.core.config.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidacaoEntidadeException extends NegocioException {

    public ValidacaoEntidadeException(String mensagem) {
        super(mensagem);
    }

}
