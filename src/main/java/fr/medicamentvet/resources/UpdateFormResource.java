package fr.medicamentvet.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import fr.medicamentvet.controller.Controller;
import fr.medicamentvet.entities.UpdateForm;

/**
 * This class provides UpdateForm object response in "application/json" format
 * for the relative "update-form" URI path.
 */
@Path("update-form")
public class UpdateFormResource {

	/**
	 * The method gives a "application/json" response to a HTTP GET request.
	 * 
	 * @return UpdateForm object in "application/json" data format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUpdateFormInputs() {

		UpdateForm updateForm = Controller.getUpdateForm();

		if (updateForm != null) {
			return Response.ok(updateForm).build();
		}
		updateForm = Controller.getUpdateFormInputs();

		return Response.ok(updateForm).build();
	}
}
