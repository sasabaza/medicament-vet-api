package fr.medicamentvet.exception;

import java.util.Map;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import fr.medicamentvet.utils.Utils;

/**
 * This class returns a response to inform client that there is a violation of
 * the constraint, especially when a parameter does not match a regular
 * expression.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {

		Set<ConstraintViolation<?>> constraintViolationsSet = exception.getConstraintViolations();

		String message = null;

		for (ConstraintViolation<?> constraintViolation : constraintViolationsSet) {
			message = constraintViolation.getMessage();
		}

		Map<String, String> errorMap = Utils.messageMap(message);

		return Response.status(Status.BAD_REQUEST).entity(errorMap).type(MediaType.APPLICATION_JSON).build();
	}
}