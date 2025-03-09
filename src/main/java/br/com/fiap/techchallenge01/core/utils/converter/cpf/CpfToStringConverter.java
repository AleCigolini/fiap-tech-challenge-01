package br.com.fiap.techchallenge01.core.utils.converter.cpf;

import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class CpfToStringConverter implements Converter<Cpf, String> {
    @Override
    public String convert(MappingContext<Cpf, String> context) {
        return context.getSource().getValue();
    }
}
