package ftn.xscience.exception;

import org.springframework.http.HttpStatus;

public class UnmarshallingException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	
	public UnmarshallingException(String message) {
		super(message);
		httpStatus = HttpStatus.CONFLICT;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
