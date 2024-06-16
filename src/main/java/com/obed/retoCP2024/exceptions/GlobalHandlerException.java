package com.obed.retoCP2024.exceptions;

import com.obed.retoCP2024.exceptions.mappers.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling exceptions thrown by controllers.
 */
@ControllerAdvice
public class GlobalHandlerException {

    /**
     * Handles MethodArgumentNotValidException.
     *
     * <p>Returns a ResponseEntity with HTTP status 400 (Bad Request) and a map of field errors.</p>
     *
     * @param ex the MethodArgumentNotValidException thrown
     * @return ResponseEntity with HTTP status 400 (Bad Request) and a map of field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
    /**
     * Handles MethodArgumentTypeMismatchException.
     *
     * <p>Returns a ResponseEntity with HTTP status 400 (Bad Request) and an error message indicating
     * the type mismatch.</p>
     *
     * @param ex the MethodArgumentTypeMismatchException thrown
     * @return ResponseEntity with HTTP status 400 (Bad Request) and an error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String error = "Parameter '" + ex.getName() + "' must be of type '" + ex.getRequiredType().getSimpleName() + "'";
        Map<String, String> errors = new HashMap<>();
        errors.put("error", error);
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles ConstraintViolationException.
     *
     * <p>Returns a ResponseEntity with HTTP status 400 (Bad Request) and a map of constraint violations.</p>
     *
     * @param ex the ConstraintViolationException thrown
     * @return ResponseEntity with HTTP status 400 (Bad Request) and a map of constraint violations
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles DataIntegrityViolationException.
     *
     * <p>Returns a ResponseEntity with HTTP status 409 (Conflict) and the exception message.</p>
     *
     * @param e the DataIntegrityViolationException thrown
     * @return ResponseEntity with HTTP status 409 (Conflict) and the exception message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * <p>Returns a ResponseEntity with HTTP status 404 (Not Found) and the exception message.</p>
     *
     * @param e the ResourceNotFoundException thrown
     * @return ResponseEntity with HTTP status 404 (Not Found) and the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles HttpMessageNotReadableException.
     *
     * <p>Returns a ResponseEntity with HTTP status 400 (Bad Request) and a generic error message for
     * invalid JSON format or missing required fields.</p>
     *
     * @param ex the HttpMessageNotReadableException thrown
     * @return ResponseEntity with HTTP status 400 (Bad Request) and a generic error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid JSON format or missing required fields.";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Handles generic Exception.
     *
     * <p>Returns a ResponseEntity with HTTP status 500 (Internal Server Error) and a generic error message
     * indicating that something went wrong.</p>
     *
     * @param e the Exception thrown
     * @return ResponseEntity with HTTP status 500 (Internal Server Error) and a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
    }
}
