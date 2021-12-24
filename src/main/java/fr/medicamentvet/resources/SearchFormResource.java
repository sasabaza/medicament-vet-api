package fr.medicamentvet.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import fr.medicamentvet.controller.Controller;
import fr.medicamentvet.entities.SearchForm;

/**
 * This class gives a "application/json" response for the SearchForm object
 * identified by "search-form" URI path.
 */
@Path("search-form")
public class SearchFormResource {

	/**
	 * The method produces a "application/json" response to a HTTP GET request.
	 * 
	 * @return SearchForm object in "application/json" data format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchFormInputs() {

		SearchForm searchForm = Controller.getSearchForm();

		if (searchForm != null) {
			return Response.ok(searchForm).build();
		}
		searchForm = Controller.getSearchFormInputs();

		return Response.ok(searchForm).build();
	}
}
