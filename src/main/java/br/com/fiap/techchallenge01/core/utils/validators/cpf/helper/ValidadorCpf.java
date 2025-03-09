package br.com.fiap.techchallenge01.core.utils.validators.cpf.helper;

public class ValidadorCpf {
    public static boolean isValidCPF(String cpf) {
        // valida se o cpf contém 11 dígitos numéricos e se não é uma sequência de números iguais
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return(false);
        }
        return true;
    }

}
