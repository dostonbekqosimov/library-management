package doston.code.controllerAdvices;


import doston.code.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        // write logic there
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> exceptionHandler(UnauthorizedException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(MemberActiveLoanException.class)
    public ResponseEntity<?> exceptionHandler(MemberActiveLoanException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<?> exceptionHandler(BookUnavailableException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> exceptionHandler(BadRequestException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> exceptionHandler(ForbiddenException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<?> exceptionHandler(DataExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> exceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}

