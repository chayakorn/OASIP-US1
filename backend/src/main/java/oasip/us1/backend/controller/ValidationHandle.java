package oasip.us1.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@ControllerAdvice
public class ValidationHandle extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<Object,Object> errors = new HashMap<>();
        Map<String,String> fielderrors = new HashMap<>();
        errors.put("timestamp", Instant.now().atZone(ZoneId.of("Asia/Bangkok")));
        errors.put("status",status);
        errors.put("path", ((ServletWebRequest)request).getRequest().getRequestURI().toString());
        errors.put("error","Bad Request");
        errors.put("message","Validation failed");
        errors.put("fieldErrors",fielderrors);
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fielderrors.put(fieldName,message);
        });
        return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
    }
}
