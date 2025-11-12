/**
 * The main entry point for the Data Management System (DMS) application.
 * <p>
 * This class initializes the database connection dialog and, upon a successful
 * connection, launches the main Movie Graphical User Interface (GUI).
 * </p>
 *
 * <p><b>Role in the System:</b></p>
 * <ul>
 *     <li>Starts the DMS application execution.</li>
 *     <li>Handles the initial database connection setup through {@link DBConnectionDialog}.</li>
 *     <li>Initializes the {@link MovieDatabaseManager} to manage movie records once connected.</li>
 * </ul>
 *
 * <p><b>Dependencies:</b> Requires the following classes to function:</p>
 * <ul>
 *     <li>{@link DBConnectionDialog} — for database connection input and validation.</li>
 *     <li>{@link MovieDatabaseManager} — for managing database operations (CRUD).</li>
 *     <li>{@link MovieGUI} — for rendering the main user interface.</li>
 * </ul>
 *
 * <p><b>Error Handling:</b></p>
 * If the user cancels the connection dialog or fails to connect, the program
 * safely terminates with a status message in the console.
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * // Run the DMS application
 * public static void main(String[] args) {
 *     Main.main(args);
 * }
 * }</pre>
 *
 * @author YourName
 * @version 1.0
 */
public class Main {

    /**
     * Launches the Data Management System application.
     * <p>
     * This method first opens a connection dialog using {@link DBConnectionDialog}.
     * If a valid database connection is obtained, it initializes a
     * {@link MovieDatabaseManager} and launches the {@link MovieGUI}.
     * </p>
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true"); // Enables smoother GUI rendering

        // Display connection dialog
        java.sql.Connection connection = DBConnectionDialog.showDialog(null);

        // Stop execution if no connection was established
        if (connection == null) {
            System.out.println("No database connection. Program exiting.");
            return;
        }

        // Start application components
        MovieDatabaseManager db = new MovieDatabaseManager(connection);
        new MovieGUI(db);
    }
}
