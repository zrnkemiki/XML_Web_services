package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class TokenMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public TokenMissingException(HttpStatus status, String message) {
		super(message);
		this.httpStatus=  status;
	}
	
	public TokenMissingException(String message) {
		super(message);
		this.httpStatus = HttpStatus.UNAUTHORIZED;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
