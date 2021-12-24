package fr.medicamentvet.resources;

import java.util.Map;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import fr.medicamentvet.controller.Controller;
import fr.medicamentvet.entities.MedicamentSearch;

/**
 * This class contains 3 responses in "application/json" format: a list of all
 * Medicament objects, nomId map object response, and a search result list of
 * names of the medicaments.
 */
@Path("medicaments")
public class MedicamentsResource {

	private static final String PATH_NOM_ID = "nom-id";
	private static final String PATH_SEARCH = "search";

	/**
	 * The method returns a "application/json" response to a HTTP GET request
	 * defined by "nom-id" URI path.
	 * 
	 * @return List of names and ids in "application/json" data
	 *         format
	 */
	@GET
	@Path(PATH_NOM_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMedicamentsNomId() {

		Map<String, Integer> nomIdMap = Controller.getNomIdMap();

		if (nomIdMap != null) {
			return Response.ok(nomIdMap).build();
		}
		nomIdMap = Controller.getAllMedicamentsNomId();

		return Response.ok(nomIdMap).build();
	}

	/**
	 * The method generates a "application/json" response to a HTTP GET request
	 * given the MedicamentSearch object parameter.
	 * 
	 * @param medicamentSearch MedicamentSearch object in "application/json"
	 * @return List of search result names
	 */
	@POST
	@Path(PATH_SEARCH)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchMedicamentNames(MedicamentSearch medicamentSearch) {

		return Response.ok(Controller.searchMedicamentNames(medicamentSearch)).build();
	}
}
