/**
 * 
 */
package com.myne.exception;

/**
 * @author mahendra
 *
 */
public class ErrorResponse {
	private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    // Getter methods

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}
    
}
