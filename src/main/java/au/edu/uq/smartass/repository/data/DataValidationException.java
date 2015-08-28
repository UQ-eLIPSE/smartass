package au.edu.uq.smartass.repository.data;

public class DataValidationException extends RuntimeException{

	public DataValidationException() {
	}

	public DataValidationException(String message) {
		super(message);
	}
}
