package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class XMLConnectionPoolEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public XMLConnectionPoolEmptyException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
	
	public XMLConnectionPoolEmptyException(String message) {
		super(message);
		this.httpStatus = HttpStatus.TOO_MANY_REQUESTS;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
