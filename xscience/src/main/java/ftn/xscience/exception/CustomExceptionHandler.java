package ftn.xscience.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CollectionEmptyException.class)
	public ResponseEntity<ExceptionResponse> handleCollectionEmptyException(CollectionEmptyException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(DocumentAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleDocumentAlreadyExistsException(DocumentAlreadyExistsException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(DocumentNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleDocumentNotFoundException(DocumentNotFoundException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(DOMParsingFailedException.class)
	public ResponseEntity<ExceptionResponse> handleDOMParsingFailedException(DOMParsingFailedException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(TokenMissingException.class)
	public ResponseEntity<ExceptionResponse> handleTokenMissingException(TokenMissingException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(UnmarshallingException.class)
	public ResponseEntity<ExceptionResponse> handleUnmarshallingException(UnmarshallingException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	
	@ExceptionHandler(XMLConnectionPoolEmptyException.class)
	public ResponseEntity<ExceptionResponse> handleXMLConnectionPoolEmptyException(XMLConnectionPoolEmptyException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, e.getHttpStatus());
	}
	

}
