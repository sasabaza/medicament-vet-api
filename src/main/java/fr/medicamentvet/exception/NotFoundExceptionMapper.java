package fr.medicamentvet.exception;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import fr.medicamentvet.utils.Utils;

/**
 * This class returns a "application/json" response with 404 status code when
 * there is a NotFoundException.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {

		String message = exception.getMessage();

		Map<String, String> errorMap = Utils.messageMap(message);

		return Response.status(Status.NOT_FOUND).entity(errorMap).type(MediaType.APPLICATION_JSON).build();
	}
}