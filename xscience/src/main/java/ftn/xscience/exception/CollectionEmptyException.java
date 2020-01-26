package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class CollectionEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public CollectionEmptyException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
	
	public CollectionEmptyException(String message) {
		super(message);
		this.httpStatus = HttpStatus.NO_CONTENT;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
