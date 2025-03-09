package br.com.fiap.techchallenge01.core.utils.validators.cpf;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cpf {
    String message() default "O formato do CPF é inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
