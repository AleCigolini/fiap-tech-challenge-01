package br.com.fiap.techchallenge01.core.config.exception.handler;

import br.com.fiap.techchallenge01.core.config.exception.domain.Problema;
import br.com.fiap.techchallenge01.core.config.exception.domain.ProblemaType;
import br.com.fiap.techchallenge01.core.config.exception.exceptions.EntidadeNaoEncontradaException;
import br.com.fiap.techchallenge01.core.config.exception.exceptions.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Desculpe, algo deu errado. " +
            "Estamos enfrentando um problema interno inesperado. " +
            "Por favor, tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte.";


    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemaType problemaType = ProblemaType.ERRO_SISTEMA;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemaType problemaType = ProblemaType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemaType problemaType = ProblemaType.VIOLACAO_REGRAS_NEGOCIO;
        String detail = ex.getMessage();

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemaType problemaType = ProblemaType.DADOS_INVALIDOS;
        String detail = "Operação não permitida devido à violação de integridade dos dados.";

        Problema problema = createProblemBuilder(status, problemaType, detail)
                .mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemaType problemaType = ProblemaType.RECURSO_NAO_ENCONTRADO;
        String requestUri = request.getDescription(false).replace("uri=", "");

        String detail = String.format("O recurso [ %s ], que você tentou acessar, é inexistente.", requestUri);

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        List<Problema.ErroAtributo> erroAtributos = ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }

            return Problema.ErroAtributo.builder().nomeAtributo(name).mensagemErro(message).build();
        }).collect(Collectors.toList());

        Problema problema = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemaType.DADOS_INVALIDOS, detail)
                .mensagemUsuario(detail).listaErroAtributos(erroAtributos)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> mensagensErro = new ArrayList<>();

        for (ParameterValidationResult result : ex.getAllValidationResults()) {
            for (MessageSourceResolvable error : result.getResolvableErrors()) {
                String errorMessage = error.getDefaultMessage();
                mensagensErro.add(errorMessage);
            }
        }

        Problema problema = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemaType.DADOS_INVALIDOS, "Erro na validação")
                .mensagemUsuario(mensagensErro.isEmpty() ? "Erro de validação." : String.join(", ", mensagensErro))
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemaType problemaType = ProblemaType.METODO_NAO_PERMITIDO;

        String requestUri = request.getDescription(false).replace("uri=", "");

        String detail = String.format("O método [ %s ] não é permitido para o recurso [ %s ].", ex.getMethod(), requestUri);

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemaType problemaType = ProblemaType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                         HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemaType problemaType = ProblemaType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format(
                "A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemaType problemaType = ProblemaType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemaType problemaType = ProblemaType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problema.ErroAtributo> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }

            return Problema.ErroAtributo.builder().nomeAtributo(name).mensagemErro(message).build();
        }).collect(Collectors.toList());

        Problema problema = createProblemBuilder(status, problemaType, detail).mensagemUsuario(detail).listaErroAtributos(problemObjects)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private Problema.ProblemaBuilder createProblemBuilder(HttpStatusCode status, ProblemaType problemaType, String detail) {

        return Problema.builder().dataHora(OffsetDateTime.now()).status(status.value()).tipo(problemaType.getUri())
                .titulo(problemaType.getTitulo()).detalhe(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

}
