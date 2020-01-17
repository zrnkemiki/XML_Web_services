package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public DocumentNotFoundException(String message) {
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
