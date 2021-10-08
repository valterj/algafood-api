package com.algaworks.algafood.api.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.exceptionhandler.Problem.Field;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno no sistema.";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers,
                    status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers,
                    status, request);
        }

        var detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Problem problem = createProblemBuilder(status,
                ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                        .userMessage(MSG_ERRO_GENERICO)
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' não existe.", path);

        Problem problem = createProblemBuilder(status,
                ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                        .userMessage(MSG_ERRO_GENERICO)
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' recebeu o valor '%s'"
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status,
                ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                        .userMessage(MSG_ERRO_GENERICO)
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult br = ex.getBindingResult();

        List<Problem.Field> problemFields = br.getFieldErrors().stream()
                .map(toProblemField())
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status,
                ProblemType.DADOS_INVALIDOS, detail)
                        .userMessage(detail)
                        .fields(problemFields)
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Function<? super FieldError, ? extends Field> toProblemField() {
        return fieldError -> Problem.Field.builder()
                .name(fieldError.getField())
                .userMessage(fieldError.getDefaultMessage())
                .build();
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradoException(
            EntidadeNaoEncontradaException ex, WebRequest request) {

        var status = NOT_FOUND;

        Problem problem = createProblemBuilder(status,
                ProblemType.ENTIDADE_NAO_ENCONTRADA, ex.getMessage())
                        .userMessage(ex.getMessage())
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException ex, WebRequest request) {

        var status = BAD_REQUEST;

        Problem problem = createProblemBuilder(status,
                ProblemType.ERRO_NEGOCIO, ex.getMessage())
                        .userMessage(ex.getMessage())
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(
            EntidadeEmUsoException ex, WebRequest request) {

        var status = CONFLICT;

        Problem problem = createProblemBuilder(status,
                ProblemType.ENTIDADE_EM_USO, ex.getMessage())
                        .userMessage(ex.getMessage())
                        .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
            ProblemType problemType, String detail) {

        return Problem.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

}
