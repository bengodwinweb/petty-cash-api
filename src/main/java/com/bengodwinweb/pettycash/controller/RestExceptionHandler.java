package com.bengodwinweb.pettycash.controller;

import com.bengodwinweb.pettycash.dto.model.error.ApiError;
import com.bengodwinweb.pettycash.dto.model.error.ApiSubError;
import com.bengodwinweb.pettycash.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    private ResponseEntity<Object> buildResponseEntity (ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> expiredJwt(ExpiredJwtException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.UNAUTHORIZED).setMessage("JWT Token has expired").setDebugMessage("Please login"));
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> unauthorized(UnauthorizedException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.UNAUTHORIZED).setMessage("Unauthorized").setDebugMessage(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> illegalArgument(IllegalArgumentException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.BAD_REQUEST).setMessage("Illegal Argument Exception").setDebugMessage(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> authentication(AuthenticationException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.BAD_REQUEST).setMessage("Authentication Error").setDebugMessage(e.getMessage()));
    }

    @ExceptionHandler(EmailExistsException.class)
    protected ResponseEntity<Object> handleEmailExists(EmailExistsException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleNewUserValidation(ValidationException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);

        List<ApiSubError> subErrors = new ArrayList<>();
        for (FieldError fieldError : e.getErrors().getFieldErrors()) {
            ApiSubError subError = new ApiSubError(fieldError.getField(), fieldError.getDefaultMessage());
            subErrors.add(subError);
        }
        for (ObjectError objectError : e.getErrors().getGlobalErrors()) {
            ApiSubError subError = new ApiSubError(objectError.getObjectName(), objectError.getDefaultMessage());
            subErrors.add(subError);
        }

        return buildResponseEntity(apiError.setMessage("Validation Error").setDebugMessage(e.getMessage()).setSubErrors(subErrors));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(NotFoundException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.NOT_FOUND).setMessage("Not found").setDebugMessage(e.getMessage()));
    }

    @ExceptionHandler(NonmatchingTotalException.class)
    protected ResponseEntity<Object> handleNonmatchingTotal(NonmatchingTotalException e) {
        return buildResponseEntity(new ApiError().setStatus(HttpStatus.BAD_REQUEST).setMessage("Box totals do not match").setDebugMessage(e.getMessage()));
    }
}
