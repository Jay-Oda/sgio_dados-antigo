package cv.pn.processmanagement.exceptions;

import cv.pn.processmanagement.utilities.APIResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final String RECORD_NOT_FOUND = "RECORD NOT FOUND";
    private final String VALIDATION_FAILED = "VALIDATION FAILED";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleUserNotFoundException
            (Exception ex, WebRequest request) {

        APIResponse error =  new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(RECORD_NOT_FOUND)
                .setDetails(Collections.singletonList(ex.getMessage())).builder();

        return new ResponseEntity<Object>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException
            (Exception ex) {

        APIResponse error =  new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(VALIDATION_FAILED)
                .setDetails(Collections.singletonList(ex.getMessage())).builder();

        return new ResponseEntity<Object>(error , HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Object> details = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        APIResponse error =  new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(RECORD_NOT_FOUND)
                .setDetails(details).builder();


        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        APIResponse error =  new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(VALIDATION_FAILED)
                .setDetails(Collections.singletonList(ex.getMessage())).builder();

        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        APIResponse error =  new APIResponse.buildAPIResponse()
                .setStatus(false)
                .setStatusText(VALIDATION_FAILED)
                .setDetails(Collections.singletonList(ex.getMessage())).builder();

        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
}
