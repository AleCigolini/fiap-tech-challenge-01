package br.com.fiap.techchallenge01.core.utils.converter.email;

import br.com.fiap.techchallenge01.core.utils.domain.Email;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class StringToEmailConverter implements Converter<String, Email> {
    @Override
    public Email convert(MappingContext<String, Email> context) {
        return new Email(context.getSource());
    }
}
