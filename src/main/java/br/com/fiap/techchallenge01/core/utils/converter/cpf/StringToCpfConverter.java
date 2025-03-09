package br.com.fiap.techchallenge01.core.utils.converter.cpf;

import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class StringToCpfConverter implements Converter<String, Cpf> {
    @Override
    public Cpf convert(MappingContext<String, Cpf> context) {
        return new Cpf(context.getSource());
    }
}
