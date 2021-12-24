package fr.medicamentvet.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.medicamentvet.utils.ApplicationLogger;

/**
 * This class delivers a Connection object to execute query statements on
 * tables.
 */
public final class CreateConnection {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("jdbc");
	private static final String JNDI = RESOURCE_BUNDLE.getString("jdbc.jndi");

	private CreateConnection() {
		super();
	}

	public static Connection getDSConnection() {
		Connection connection = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(JNDI);
			connection = dataSource.getConnection();
		} catch (NamingException | SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return connection;
	}
}
