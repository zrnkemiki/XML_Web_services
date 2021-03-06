package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class DOMParsingFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public DOMParsingFailedException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
	
	public DOMParsingFailedException(String message) {
		super(message);
		this.httpStatus = HttpStatus.CONFLICT;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
