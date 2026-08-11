package com.carauction.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class) 
    ResponseEntity<ApiError> conflict(DuplicateResourceException e,HttpServletRequest r) {
        return build(HttpStatus.CONFLICT,e.getMessage(),r,Map.of());
    }

    @ExceptionHandler(ResourceNotFoundException.class) 
    ResponseEntity<ApiError> notFound(ResourceNotFoundException e,HttpServletRequest r) {
        return build(HttpStatus.NOT_FOUND,e.getMessage(),r,Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) 
    ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r) {
        Map<String,String> fields=new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(x->fields.putIfAbsent(x.getField(),x.getDefaultMessage()));

        return build(HttpStatus.BAD_REQUEST,"Validation failed",r,fields);
    }

    private ResponseEntity<ApiError> build(HttpStatus s,String m,HttpServletRequest r,Map<String,String> v) {
        return ResponseEntity.status(s).body(new ApiError(OffsetDateTime.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI(),v));
    }
}