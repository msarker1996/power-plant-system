package com.brac.power.plant.system.advice;

import com.brac.power.plant.system.dto.*;
import com.brac.power.plant.system.exception.ExistingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ParameterErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {

        List<ParameterErrorDetail> errors = new ArrayList<>();

        Map<String, String> validationErrors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            int lastIndex = propertyPath.lastIndexOf('.');
            errors.add(new ParameterErrorDetail(lastIndex != -1 ? propertyPath.substring(lastIndex + 1) : propertyPath, violation.getMessage()));
        }

        ParameterErrorResponse parameterErrorResponse = new ParameterErrorResponse();
        parameterErrorResponse.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        parameterErrorResponse.setStatus("Bed Request");
        parameterErrorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        parameterErrorResponse.setMessage("Validation failed");
        parameterErrorResponse.setPath(request.getRequestURI());
        parameterErrorResponse.setData(new ParameterErrorData(errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parameterErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        errorResponse.setStatus("Bed Request");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setData(new ErrorData(errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExistingException.class)
    public Map<String, String> handleExistingException(ExistingException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        response.put("status", "Exist");
        response.put("code", String.valueOf(HttpStatus.NOT_FOUND.value()));
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());
        return response;
    }



}
