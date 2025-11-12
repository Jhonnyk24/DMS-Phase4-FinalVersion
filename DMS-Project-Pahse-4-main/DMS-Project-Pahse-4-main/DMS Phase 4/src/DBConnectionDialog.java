import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A graphical dialog that collects and validates user input for establishing a database connection.
 * <p>
 * This class allows the user to input the host, database name, username, and password, then
 * attempts to connect to a MySQL or SQLite database. It also provides error handling for
 * missing or invalid connection details.
 * </p>
 *
 * <p><b>Role in the System:</b></p>
 * Serves as the first interaction point in the Data Management System (DMS), providing
 * connection details to initialize the {@link java.sql.Connection} object used throughout
 * the application by classes such as {@link MovieDatabaseManager}.
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *     <li>Uses Java Swing for user interface components.</li>
 *     <li>Uses JDBC for establishing a connection to the selected database.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * Connection conn = DBConnectionDialog.showDialog(null);
 * if (conn != null) {
 *     MovieDatabaseManager db = new MovieDatabaseManager(conn);
 *     new MovieGUI(db);
 * }
 * }</pre>
 *
 * @author YourName
 * @version 1.0
 */
public class DBConnectionDialog extends JDialog {

    private JTextField hostField;
    private JTextField dbField;
    private JTextField userField;
    private JPasswordField passField;
    private JButton connectButton;
    private JButton cancelButton;
    private Connection connection;

    /**
     * Constructs a {@code DBConnectionDialog} with fields for entering connection details.
     *
     * @param parent the parent frame for centering the dialog
     */
    public DBConnectionDialog(Frame parent) {
        super(parent, "Database Connection", true);
        initializeUI();
        setupEventHandlers();
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Initializes and arranges Swing components inside the dialog.
     * <p>
     * Creates labeled fields for host, database name, username, and password,
     * along with Connect and Cancel buttons.
     * </p>
     */
    private void initializeUI() {
        setLayout(new GridLayout(5, 2, 10, 10));

        hostField = new JTextField("localhost");
        dbField = new JTextField();
        userField = new JTextField("root");
        passField = new JPasswordField();

        connectButton = new JButton("Connect");
        cancelButton = new JButton("Cancel");

        add(new JLabel("Host:"));
        add(hostField);
        add(new JLabel("Database:"));
        add(dbField);
        add(new JLabel("Username:"));
        add(userField);
        add(new JLabel("Password:"));
        add(passField);
        add(connectButton);
        add(cancelButton);
    }

    /**
     * Registers action listeners for buttons.
     * <p>
     * - Clicking “Connect” validates input fields and attempts to establish a connection.<br>
     * - Clicking “Cancel” closes the dialog without connecting.
     * </p>
     */
    private void setupEventHandlers() {
        connectButton.addActionListener(this::onConnectClicked);
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Handles the connection attempt when the “Connect” button is clicked.
     * <p>
     * Validates that all required fields are filled and attempts to connect using
     * the provided information. Displays error dialogs for invalid inputs or
     * failed connection attempts.
     * </p>
     *
     * @param e the {@link ActionEvent} triggered by the Connect button
     */
    private void onConnectClicked(ActionEvent e) {
        String host = hostField.getText().trim();
        String dbName = dbField.getText().trim();
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());

        // Input validation
        if (host.isEmpty() || dbName.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields except password are required.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build connection URL
        String url = "jdbc:mysql://" + host + "/" + dbName + "?useSSL=false";

        try {
            connection = DriverManager.getConnection(url, username, password);
            JOptionPane.showMessageDialog(this,
                    "Connection successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to connect: " + ex.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the dialog and returns a valid database connection if successful.
     * <p>
     * If the user cancels or the connection fails, this method returns {@code null}.
     * </p>
     *
     * @param parent the parent frame for dialog positioning
     * @return a valid {@link Connection} object if connected successfully; otherwise {@code null}
     */
    public static Connection showDialog(Frame parent) {
        DBConnectionDialog dialog = new DBConnectionDialog(parent);
        dialog.setVisible(true);
        return dialog.connection;
    }
}
