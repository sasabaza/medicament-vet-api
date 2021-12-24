package fr.medicamentvet.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.ws.rs.core.Response;

import fr.medicamentvet.entities.Medicament;
import fr.medicamentvet.entities.MedicamentSearch;
import fr.medicamentvet.entities.SearchForm;
import fr.medicamentvet.entities.UpdateForm;
import fr.medicamentvet.persistence.PersistenceService;
import fr.medicamentvet.utils.Utils;

/**
 * The purpose of the class is to manage communication between the Resources
 * package and the PersistenceService class. The Controller class saves main
 * objects : the nomIdMap i.e. Nom du médicament and id entry, the SearchForm
 * and the UpdateForm objects.
 */
public final class Controller {

//	private static final String PATH_FOLDER = System.getProperty("user.home") + "/Application/";
	private static final String PATH_FOLDER = System.getProperty("user.home") + "\\Application\\";

	private static Map<String, Integer> nomIdMap;
	private static Map<Integer, String> idNomMap;
	private static SearchForm searchForm;
	private static UpdateForm updateForm;

	private Controller() {
		super();
	}

	/**
	 * The method returns a Response given the id of a Medicament object.
	 * 
	 * @param id id of the Medicament object
	 * @return Response
	 */
	public static Response getMedicamentById(int id) {

		return Response.ok(PersistenceService.getMedicamentById(id)).build();
	}

	/**
	 * The purpose of the method is to delete all data associated with Medicament
	 * object and files, directories.
	 * 
	 * @param id id of the Medicament object
	 * @return Empty response if status is OK or response error when there is
	 *         IOException
	 */
	public static Response deleteMedicamentById(int id) {
		PersistenceService.deleteMedicamentById(id);

		String path = PATH_FOLDER + id;
		Path directory = Paths.get(path);

		return Utils.deleteDirectory(directory);
	}

	/**
	 * The method gets all noms and associated ids (K,V) of the Medicaments,
	 * initializes the nomIdMap and the idNomMap objects.
	 * 
	 * @return {@code HashMap<String, Integer>}
	 */
	public static Map<String, Integer> getAllMedicamentsNomId() {
		nomIdMap = PersistenceService.getAllMedicamentsNomId();

		idNomMap = nomIdMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

		return nomIdMap;
	}

	/**
	 * The method performs a search with the MedicamentSearch data, and returns a
	 * list of names of the medicaments.
	 * 
	 * @param medicamentSearch MedicamentSearch object
	 * @return List of names of the medicaments
	 */
	public static List<String> searchMedicamentNames(MedicamentSearch medicamentSearch) {
		return PersistenceService.searchMedicamentNames(medicamentSearch);
	}

	/**
	 * The method gets the inputs for the SearchForm object from the
	 * PersistenceService class, and initializes the searchForm object.
	 * 
	 * @return SearchForm object
	 */
	public static SearchForm getSearchFormInputs() {
		searchForm = PersistenceService.getSearchFormInputs();

		return searchForm;
	}

	/**
	 * The method gets the inputs for the UpdateForm object from the
	 * PersistenceService class, and initializes the updateForm object.
	 * 
	 * @return UpdateForm object
	 */
	public static UpdateForm getUpdateFormInputs() {
		updateForm = PersistenceService.getUpdateFormInputs();

		return updateForm;
	}

	/**
	 * The method updates the data of a Medicament object from the
	 * PersistenceService class.
	 * 
	 * @param medicament Medicament object
	 * @return Medicament object
	 */
	public static Medicament updateMedicament(Medicament medicament) {
		return PersistenceService.updateMedicament(medicament);
	}

	/**
	 * The method removes entry by id from the nomIdMap object.
	 * 
	 * @param id id of the Medicament object
	 */
	public static void deleteNomIdWithId(int id) {
		String nom = idNomMap.get(id);
		nomIdMap.remove(nom);
		idNomMap.remove(id);
	}

	/**
	 * The method updates the key of the nomIdMap object if it has changed.
	 * 
	 * @param id  id of the Medicament object
	 * @param nom Name of the Medicament object
	 */
	public static void updateNomIdMap(int id, String nom) {

		// if the condition is true, then String key nom has changed
		if (!nomIdMap.containsKey(nom)) {

			// To update the key we must delete first K,V and then put new K,V
			String oldValue = idNomMap.get(id);
			nomIdMap.remove(oldValue);
			nomIdMap.put(nom, id);
			idNomMap.put(id, nom);
		}
	}

	/**
	 * The method updates the SearchForm object.
	 */
	public static void updateSearchForm() {
		searchForm = PersistenceService.getSearchFormInputs();
	}

	public static Map<String, Integer> getNomIdMap() {
		return nomIdMap;
	}

	public static Map<Integer, String> getIdNomMap() {
		return idNomMap;
	}

	public static SearchForm getSearchForm() {
		return searchForm;
	}

	public static UpdateForm getUpdateForm() {
		return updateForm;
	}
}