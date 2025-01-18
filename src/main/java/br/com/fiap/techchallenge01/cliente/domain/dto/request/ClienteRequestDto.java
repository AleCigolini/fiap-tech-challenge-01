package br.com.fiap.techchallenge01.cliente.domain.dto.request;

import br.com.fiap.techchallenge01.cliente.domain.validator.EmailOuCpf;
import br.com.fiap.techchallenge01.core.utils.validators.cpf.Cpf;
import br.com.fiap.techchallenge01.core.utils.validators.email.EmailValido;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@EmailOuCpf
public class ClienteRequestDto {
    @Size(max = 255, message = "O nome deve ter {max} caracteres")
    private String nome;
    @EmailValido
    @Size(max = 255, message = "O e-mail deve ter {max} caracteres")
    private String email;
    @Cpf
    private String cpf;
}

