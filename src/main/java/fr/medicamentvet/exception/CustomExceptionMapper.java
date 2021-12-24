package fr.medicamentvet.exception;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import fr.medicamentvet.utils.ApplicationLogger;
import fr.medicamentvet.utils.Utils;

/**
 * This class is custom ExceptionMapper that implements an ExceptionMapper with
 * a Throwable. It handles most of the exceptions and returns a custom
 * "application/json" response.
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

	private static final String HTTP_INTROUVABLE_TEXT = "Requête HTTP introuvable";
	private static final String NOT_FOUND_EXCEPTION_CLASS = "NotFoundException";
	private static final String HTTP_NON_AUTORISEE_TEXT = "Requête HTTP non autorisée";
	private static final String NOT_ALLOWED_EXCEPTION_CLASS = "NotAllowedException";
	private static final String MEDIA_NON_SUPPORTE_TEXT = "Type de média non supporté";
	private static final String NOT_SUPPORTED_EXCEPTION_CLASS = "NotSupportedException";
	private static final String HTTP_NON_ACCEPTABLE_TEXT = "Requête HTTP non acceptable";
	private static final String NOT_ACCEPTABLE_EXCEPTION_CLASS = "NotAcceptableException";
	private static final String MAUVAISE_REQUETE_TEXT = "Mauvaise requête HTTP";
	private static final String BAD_REQUEST_EXCEPTION_CLASS = "BadRequestException";
	private static final String PARAMETRES_MANQUANTS_TEXT = "Pointeur nul - Paramètres manquants";
	private static final String NULL_POINTER_EXCEPTION_CLASS = "NullPointerException";
	private static final String ERREUR_TRAITEMENT_TEXT = "Erreur lors du traitement des données";
	private static final String PROCESSING_EXCEPTION_CLASS = "ProcessingException";

	@Override
	public Response toResponse(Throwable throwable) {

		String className = throwable.getClass().getSimpleName();

		Status status;
		Map<String, String> errorMap;

		switch (className) {
		case NOT_FOUND_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(HTTP_INTROUVABLE_TEXT);
			status = Status.NOT_FOUND;

			break;
		case NOT_ALLOWED_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(HTTP_NON_AUTORISEE_TEXT);
			status = Status.METHOD_NOT_ALLOWED;

			break;
		case NOT_SUPPORTED_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(MEDIA_NON_SUPPORTE_TEXT);
			status = Status.UNSUPPORTED_MEDIA_TYPE;

			break;
		case NOT_ACCEPTABLE_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(HTTP_NON_ACCEPTABLE_TEXT);
			status = Status.NOT_ACCEPTABLE;

			break;
		case BAD_REQUEST_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(MAUVAISE_REQUETE_TEXT);
			status = Status.BAD_REQUEST;

			break;
		case NULL_POINTER_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(PARAMETRES_MANQUANTS_TEXT);
			status = Status.BAD_REQUEST;

			break;
		case PROCESSING_EXCEPTION_CLASS:

			errorMap = Utils.messageMap(ERREUR_TRAITEMENT_TEXT);
			status = Status.BAD_REQUEST;

			break;
		default:

			String message = throwable.getMessage();
			errorMap = Utils.messageMap(message);
			status = Status.BAD_REQUEST;
		}

		ApplicationLogger.throwableLog(throwable);
		return Response.status(status).entity(errorMap).type(MediaType.APPLICATION_JSON).build();
	}
}