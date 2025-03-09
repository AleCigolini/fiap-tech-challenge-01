package br.com.fiap.techchallenge01.core.utils.domain.enums;

import lombok.Getter;

@Getter
public enum DigitoCpfEnum {
    PRIMEIRO_DIGITO(10, 0, 9),
    SEGUNDO_DIGITO(11, 0, 10);

    private final int pesoInicial;
    private final int indiceInicial;
    private final int numerosValidados;

    DigitoCpfEnum(int pesoInicial, int indiceInicial, int numerosValidados) {
        this.pesoInicial = pesoInicial;
        this.indiceInicial = indiceInicial;
        this.numerosValidados = numerosValidados;
    }

}
