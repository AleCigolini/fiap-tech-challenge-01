package br.com.fiap.techchallenge01.core.utils.converter.email;

import br.com.fiap.techchallenge01.core.utils.domain.Email;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class EmailToStringConverter implements Converter<Email, String> {
    @Override
    public String convert(MappingContext<Email, String> context) {
        return context.getSource().getEndereco();
    }
}
