package fr.medicamentvet.exception;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import fr.medicamentvet.utils.Utils;

/**
 * This class returns a "application/json" response with 503 status code when
 * there is a UnavailableException.
 */
@Provider
public class UnavailableExceptionMapper implements ExceptionMapper<UnavailableException> {

	private static final String SERVICE_NON_DISPONIBLE_TEXT = "Le service n'est pas disponible";

	@Override
	public Response toResponse(UnavailableException exception) {

		Map<String, String> errorMap = Utils.messageMap(SERVICE_NON_DISPONIBLE_TEXT);

		return Response.status(Status.SERVICE_UNAVAILABLE).entity(errorMap).type(MediaType.APPLICATION_JSON).build();
	}
}