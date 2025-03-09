package br.com.fiap.techchallenge01.core.utils.domain;

import br.com.fiap.techchallenge01.core.config.exception.exceptions.ValidacaoEntidadeException;
import br.com.fiap.techchallenge01.core.utils.domain.enums.DigitoCpfEnum;

import java.util.InputMismatchException;

public class Cpf {
    private final String value;

    public Cpf(String value) {
        if (!isValid(value)) {
            throw new ValidacaoEntidadeException("CPF inválido");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String cpf) {
        // valida se o cpf contém 11 dígitos numéricos e se não é uma sequência de números iguais
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return(false);
        }
        try {
            int primeiroDigitoVerificador = calcularDigitoVerificador(cpf, DigitoCpfEnum.PRIMEIRO_DIGITO);
            if ((cpf.charAt(9) - '0') != primeiroDigitoVerificador) {
                return(false);
            }

            int segundoDigitoVerificador = calcularDigitoVerificador(cpf, DigitoCpfEnum.SEGUNDO_DIGITO);

            return (cpf.charAt(10) - '0') == segundoDigitoVerificador;
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    private static int calcularDigitoVerificador(String cpf, DigitoCpfEnum digitoCpfEnum) {
        int soma = 0, peso = digitoCpfEnum.getPesoInicial();
        for (int indice = digitoCpfEnum.getIndiceInicial(); indice < digitoCpfEnum.getNumerosValidados(); indice++) {
            int numeroAtual = cpf.charAt(indice) - '0';
            soma += (numeroAtual * peso);
            peso--;
        }
        int resto = soma % 11;
        int digitoVerificador = 11 - resto;
        return digitoVerificador < 10 ? digitoVerificador : 0;
    }
}
