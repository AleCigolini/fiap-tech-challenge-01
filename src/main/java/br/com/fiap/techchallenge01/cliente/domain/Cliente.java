package br.com.fiap.techchallenge01.cliente.domain;

import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.core.utils.domain.DominioBase;

public class Cliente extends DominioBase {

    private String id;
    private String nome;
    private String email;
    private Cpf cpf;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }
}

