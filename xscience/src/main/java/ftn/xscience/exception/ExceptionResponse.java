package ftn.xscience.exception;

public class ExceptionResponse {
	
	private String errorMessage;
	
	public ExceptionResponse() {
		
	}
	
	public ExceptionResponse(String msg) {
		super();
		this.errorMessage = msg;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
