package fr.medicamentvet.resources;

import java.io.InputStream;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMultipart;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;

import fr.medicamentvet.controller.Controller;
import fr.medicamentvet.entities.Medicament;
import fr.medicamentvet.exception.NotFoundException;
import fr.medicamentvet.exception.UnavailableException;
import fr.medicamentvet.utils.Utils;

/**
 * The class consists of several responses to a HTTP request:<br>
 * - A Medicament object response to a HTTP GET request in "application/json"
 * format<br>
 * - A "application/json" data format response to a HTTP DELETE request<br>
 * - A Medicament object response to a HTTP PUT request in "application/json"
 * format<br>
 * - A "application/octet-stream" data format response to download an image
 */
@Path("medicament")
public class MedicamentResource {

	private static final String PATH_FOLDER = System.getProperty("user.home") + "\\Application\\";
//	private static final String PATH_FOLDER = System.getProperty("user.home") + "/Application/";
	private static final String PATH_MEDICAMENT_IMAGE = "\\medicament\\image";
//	private static final String PATH_MEDICAMENT_IMAGE = "/medicament/image";
	private static final String PATH_RCP_IMAGE = "\\rcp\\image";
//	private static final String PATH_RCP_IMAGE = "/rcp/image";

	private static final String PATH_RESOURCE_ENTITY_ID = "{entityId}";
	private static final String PARAM_ENTITY_ID = "entityId";
	private static final String PATH_RESOURCE_UPDATE = "update";
	private static final String PATH_RESOURCE_MEDICAMENT_IMAGE = "image/{entityId}";
	private static final String PATH_RESOURCE_RCP_IMAGE = "rcp/image/{entityId}/{number}";
	private static final String PARAM_NUMBER = "number";

	private static final String ERREUR_INTERNE_TEXT = "Erreur interne";
	private static final String IMAGE_TYPE_JPG = "image/jpg";
	private static final String IMAGE_TYPE_JPEG = "image/jpeg";
	private static final String IMAGE_TYPE_PNG = "image/png";

	private static final String REGEX_NATURAL_INTEGER_TEXT = "^[1-9]|[1-9][0-9]{1,4}";
	private static final String ID_TEXT = "id ";
	private static final String ID_INVALIDE_TEXT = "id est invalide";
	private static final String INTROUVABLE_TEXT = " est introuvable";
	private static final String NUMBER_INVALIDE_TEXT = "Le numéro de l'image est invalide";

	private static final String METHOD_NAME_MEDICAMENTIMAGE = "getMedicamentImage() method";
	private static final String METHOD_NAME_RCPIMAGE = "getRcpImage() method";

	private static final String IMAGE_NUMBER_TEXT = ", image number ";

	/**
	 * The method produces a "application/json" response to a HTTP GET request. The
	 * response is Medicament object in "application/json" data format.
	 * 
	 * @param stringId String id of the Medicament object must match regular
	 *                 expression between 1 and 5 digits natural number
	 * @return Medicament object in "application/json" data format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException idNomMap object is null
	 */
	@GET
	@Path(PATH_RESOURCE_ENTITY_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedicamentById(
			@PathParam(PARAM_ENTITY_ID) @Pattern(regexp = REGEX_NATURAL_INTEGER_TEXT, message = ID_INVALIDE_TEXT) String stringId)
			throws NotFoundException, UnavailableException {

		Map<Integer, String> idNomMap = Controller.getIdNomMap();
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}

			return Controller.getMedicamentById(Integer.parseInt(stringId));
		}
		throw new UnavailableException(ID_TEXT + stringId);
	}

	/**
	 * The purpose of the method is to delete the data of the medicament and the
	 * associated image(s) by it id.
	 * 
	 * @param stringId String id of the Medicament object must match regular
	 *                 expression between 1 and 5 digits natural number
	 * @return Empty response if the status is OK or "application/json" response
	 *         error when there is IOException exception
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException idNomMap object is null
	 */
	@DELETE
	@Path(PATH_RESOURCE_ENTITY_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMedicamentById(
			@PathParam(PARAM_ENTITY_ID) @Pattern(regexp = REGEX_NATURAL_INTEGER_TEXT, message = ID_INVALIDE_TEXT) String stringId)
			throws NotFoundException, UnavailableException {

		Map<Integer, String> idNomMap = Controller.getIdNomMap();
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}

			return Controller.deleteMedicamentById(Integer.parseInt(stringId));
		}
		throw new UnavailableException(ID_TEXT + stringId);
	}

	/**
	 * The method achieves an update of a given Medicament object. It updates the
	 * data and the images.
	 * 
	 * @param inputStream inputStream is "multipart/form-data" containing Medicament
	 *                    data and possibly images
	 * @param uriInfo     uriInfo helps to generate the URI of an image
	 * @return Medicament object in "application/json" data format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws MessagingException   MimeMultipart can not be created
	 * @throws UnavailableException idNomMap object is null
	 */
	@PUT
	@Path(PATH_RESOURCE_UPDATE)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateMedicament(InputStream inputStream, @Context UriInfo uriInfo)
			throws NotFoundException, MessagingException, UnavailableException {

		MimeMultipart multipart = getMultipart(inputStream);

		if (multipart != null) {

			Medicament medicament = getMedicamentFromMultipart(multipart, uriInfo);

			return Response.ok(Controller.updateMedicament(medicament)).build();
		}
		Map<String, String> errorMap = Utils.messageMap(ERREUR_INTERNE_TEXT);

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMap).build();
	}

	/**
	 * The method allows the client to download an image by Medicament object id.
	 * The header of the response includes the image extension.
	 * 
	 * @param stringId String id of the Medicament object must match regular
	 *                 expression between 1 and 5 digits natural number
	 * @return Image in "application/octet-stream" format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException idNomMap object is null
	 */
	@GET
	@Path(PATH_RESOURCE_MEDICAMENT_IMAGE)
	@Produces({ MediaType.APPLICATION_OCTET_STREAM, IMAGE_TYPE_JPG, IMAGE_TYPE_JPEG, IMAGE_TYPE_PNG })
	public Response getMedicamentImage(
			@PathParam(PARAM_ENTITY_ID) @Pattern(regexp = REGEX_NATURAL_INTEGER_TEXT, message = ID_INVALIDE_TEXT) String stringId)
			throws NotFoundException, UnavailableException {

		Map<Integer, String> idNomMap = Controller.getIdNomMap();
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}
			String pathImage = PATH_FOLDER + stringId + PATH_MEDICAMENT_IMAGE;

			return findFileName(pathImage, METHOD_NAME_MEDICAMENTIMAGE);
		}
		throw new UnavailableException(ID_TEXT + stringId);
	}

	/**
	 * The method allows the client to download an image by Medicament object id and
	 * an image number from the Rcp object. The header of the response includes the
	 * image extension.
	 * 
	 * @param stringId     String id of the Medicament object must match regular
	 *                     expression between 1 and 5 digits natural number
	 * @param stringNumber String number of image number. Parameter must match
	 *                     regular expression between 1 and 5 digits natural number
	 * @return Image in "application/octet-stream" format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException idNomMap object is null
	 */
	@GET
	@Path(PATH_RESOURCE_RCP_IMAGE)
	@Produces({ MediaType.APPLICATION_OCTET_STREAM, IMAGE_TYPE_JPG, IMAGE_TYPE_JPEG, IMAGE_TYPE_PNG })
	public Response getRcpImage(
			@PathParam(PARAM_ENTITY_ID) @Pattern(regexp = REGEX_NATURAL_INTEGER_TEXT, message = ID_INVALIDE_TEXT) String stringId,
			@PathParam(PARAM_NUMBER) @Pattern(regexp = REGEX_NATURAL_INTEGER_TEXT, message = NUMBER_INVALIDE_TEXT) String stringNumber)
			throws NotFoundException, UnavailableException {

		Map<Integer, String> idNomMap = Controller.getIdNomMap();
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}
			String pathImage = PATH_FOLDER + stringId + PATH_RCP_IMAGE + stringNumber;

			return findFileName(pathImage, METHOD_NAME_RCPIMAGE);
		}
		throw new UnavailableException(ID_TEXT + stringId + IMAGE_NUMBER_TEXT + stringNumber);
	}

	/**
	 * The method returns a response constructed with a file or an error if the file
	 * is not found.
	 * 
	 * @param filePath   File path
	 * @param methodName The name of parent method name
	 * @return File or an error message response
	 * @throws NotFoundException File name is not found given the filePath parameter
	 */
	private Response findFileName(String filePath, String methodName) throws NotFoundException {
		return Response.ok().build();
	}

	/**
	 * The method returns a MimeMultipart given the inputStream parameter.
	 * 
	 * @param inputStream inputStream is "multipart/form-data" containing Medicament
	 *                    data and possibly images
	 * @return MimeMultipart or null if inputStream parameter is null
	 */
	private MimeMultipart getMultipart(InputStream inputStream) {
		return new MimeMultipart();
	}

	/**
	 * The method generates the path of the images if the MimeMultipart contains
	 * input stream for each image and returns the Medicament object.
	 * 
	 * @param multipart MimeMultipart multi-part message
	 * @param uriInfo   uriInfo helps to generate the URI of an image
	 * @return Medicament object
	 * @throws NotFoundException    id of Medicament object does not exist
	 * @throws MessagingException   MimeMultipart body part is not found
	 * @throws UnavailableException idNomMap object is null
	 */
	private Medicament getMedicamentFromMultipart(MimeMultipart multipart, UriInfo uriInfo)
			throws NotFoundException, MessagingException, UnavailableException {
		return new Medicament();
	}
}
