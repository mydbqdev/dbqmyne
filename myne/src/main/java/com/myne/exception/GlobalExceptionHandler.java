/**
 * 
 */
package com.myne.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author mahendra
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(WebServiceException.class)
    public ResponseEntity<ErrorResponse> handleException(WebServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
}
