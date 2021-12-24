package fr.medicamentvet.exception;

/**
 * This class represents a NotFoundException object. It is mainly used to throw
 * an exception whenever id parameter does not exist.
 */
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}