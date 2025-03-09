package br.com.fiap.techchallenge01.core.config.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(List<org.modelmapper.Converter<?, ?>> converters) {
        var modelMapper = new ModelMapper();

        converters.forEach(modelMapper::addConverter);

        return modelMapper;
    }

}
