package fr.medicamentvet.application;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * The class is the root of the REST service. The path of the root is "api".
 */
@ApplicationPath("api")
public final class ApplicationConfiguration extends Application {

}