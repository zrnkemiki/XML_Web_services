package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class DocumentAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public DocumentAlreadyExistsException(String message) {
		super(message);
		httpStatus = HttpStatus.BAD_REQUEST;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
