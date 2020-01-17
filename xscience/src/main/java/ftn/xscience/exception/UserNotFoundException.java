package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public UserNotFoundException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
	
	public UserNotFoundException(String message) {
		super(message);
		this.httpStatus = HttpStatus.NOT_FOUND;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
