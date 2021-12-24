package fr.medicamentvet.utils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * The class contains various methods employed in the others classes.
 */
public final class Utils {

	private static final String MESSAGE_TEXT = "message";
	private static final String ERREUR_INTERNE_TEXT = "Erreur interne";

	private static final int MAX_DEPTH = 2;

	private static final String RESPONSE_DELETE_DIRECTORY = "Response Ok from deleteDirectory() method - delete directory: ";

	private Utils() {
		super();
	}

	/**
	 * The method receives a String message and returns a
	 * {@code HashMap<String, String>}.
	 * 
	 * @param message String
	 * @return {@code HashMap<String, String>} where the key is String "message" and
	 *         the value the message parameter
	 */
	public static Map<String, String> messageMap(String message) {
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put(MESSAGE_TEXT, message);

		return messageMap;
	}

	/**
	 * The method deletes the directory and all the content.
	 * 
	 * @param directory Path of the directory
	 * @return Empty response if the status is OK or an error message when there is
	 *         an IOException
	 */
	public static Response deleteDirectory(Path directory) {

		DeleteFileVisitor deleteFileVisitor = new DeleteFileVisitor();
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

		try {
			Files.walkFileTree(directory, opts, MAX_DEPTH, deleteFileVisitor);

			ApplicationLogger.log(RESPONSE_DELETE_DIRECTORY + directory.toString());
			return Response.ok().build();
		} catch (IOException e) {
			Map<String, String> errorMap = messageMap(ERREUR_INTERNE_TEXT);

			ApplicationLogger.throwableLog(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMap).build();
		}
	}
}