package com.fujitsu.deliveryfee.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Global exception handler for the application, providing centralized exception handling
 * across all @RequestMapping methods through @ExceptionHandler methods.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnsupportedCityException.class)
    public ResponseEntity<String> handleUnsupportedCityException(UnsupportedCityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedVehicleTypeException.class)
    public ResponseEntity<String> handleUnsupportedVehicleTypeException(UnsupportedVehicleTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter value: " + ex.getMessage());
    }

    @ExceptionHandler(WeatherDataUnavailableException.class)
    public ResponseEntity<String> handleWeatherDataUnavailableException(WeatherDataUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VehicleUseForbiddenException.class)
    public ResponseEntity<String> handleVehicleUseForbiddenException(VehicleUseForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(WeatherDataProcessingException.class)
    public ResponseEntity<String> handleWeatherDataProcessingException(WeatherDataProcessingException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(FeeRuleNotFoundException.class)
    public ResponseEntity<String> handleFeeRuleNotFoundException(FeeRuleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    /**
     * Generic exception handler to catch and handle all other exceptions not specifically
     * addressed by other @ExceptionHandler methods. Provides a generic error response.
     *
     * @param ex The exception that was not caught by specific handlers.
     * @return A ResponseEntity containing the error message and an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("An unexpected error occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

}