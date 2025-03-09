package br.com.fiap.techchallenge01.cliente.domain;

import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.core.utils.domain.DominioBase;
import br.com.fiap.techchallenge01.core.utils.domain.Email;

public class Cliente extends DominioBase {

    private String id;
    private String nome;
    private Email email;
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }
}

