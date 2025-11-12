import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Represents the main graphical user interface (GUI) of the Movie Data Management System (DMS).
 * <p>
 * This class provides an interactive table view of all movies stored in the database
 * and allows the user to perform basic CRUD (Create, Read, Update, Delete) operations.
 * </p>
 *
 * <p><b>Role in the System:</b></p>
 * <ul>
 *     <li>Acts as the main control hub for user interactions with the movie database.</li>
 *     <li>Communicates directly with {@link MovieDatabaseManager} to read and update data.</li>
 *     <li>Uses {@link MovieDialogGUI} for adding and editing movie entries.</li>
 * </ul>
 *
 * <p><b>Main Features:</b></p>
 * <ul>
 *     <li>Displays movie data in a styled table.</li>
 *     <li>Allows adding, editing, and deleting movies.</li>
 *     <li>Calculates and shows “scariness” scores using {@link MovieDialogGUI#showScarinessDialog(Frame, Movie)}.</li>
 *     <li>Includes elegant visual styling and hover animations.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * MovieDatabaseManager db = new MovieDatabaseManager(connection);
 * new MovieGUI(db); // launches the main GUI window
 * }</pre>
 *
 * @author YourName
 * @version 1.0
 */
public class MovieGUI extends JFrame {

    /** Table used to display movie data. */
    private JTable movieTable;

    /** Table model backing the JTable, handles dynamic updates. */
    private DefaultTableModel tableModel;

    /** Manages communication between the GUI and the database. */
    private MovieDatabaseManager db;

    /**
     * Constructs the main GUI for the Movie Data Management System.
     *
     * @param db the {@link MovieDatabaseManager} instance used to handle database operations.
     */
    public MovieGUI(MovieDatabaseManager db) {
        this.db = db;

        setTitle("Movie Database System");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== GUI Theme Setup =====
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        Color background = new Color(43, 43, 43);
        Color panelColor = new Color(58, 58, 58);
        Color textColor = new Color(255, 255, 255);
        Color gold = new Color(201, 168, 106);
        Color goldHover = new Color(227, 200, 142);

        getContentPane().setBackground(background);

        // ===== Table Setup =====
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Title", "Year", "Director", "Rating", "Runtime (min)", "Votes", "Watched"
        }, 0);

        movieTable = new JTable(tableModel);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieTable.setBackground(panelColor);
        movieTable.setForeground(textColor);
        movieTable.setSelectionBackground(gold);
        movieTable.setSelectionForeground(Color.BLACK);
        movieTable.setGridColor(gold);
        movieTable.getTableHeader().setBackground(gold);
        movieTable.getTableHeader().setForeground(Color.BLACK);
        movieTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.getViewport().setBackground(panelColor);

        // ===== Button Setup =====
        JButton addButton = new JButton("Add Movie");
        JButton editButton = new JButton("Edit Movie");
        JButton deleteButton = new JButton("Delete Movie");
        JButton scarinessButton = new JButton("Show Scariness");
        JButton refreshButton = new JButton("Refresh");

        JButton[] buttons = {addButton, editButton, deleteButton, scarinessButton, refreshButton};

        // ===== Button Styling =====
        for (JButton btn : buttons) {
            btn.setBackground(gold);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(gold, 2),
                    BorderFactory.createEmptyBorder(6, 14, 6, 14)
            ));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(goldHover);
                    btn.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(goldHover, 2),
                            BorderFactory.createEmptyBorder(6, 14, 6, 14)
                    ));
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(gold);
                    btn.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(gold, 2),
                            BorderFactory.createEmptyBorder(6, 14, 6, 14)
                    ));
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        // ===== Button Actions =====
        addButton.addActionListener(this::addMovie);
        editButton.addActionListener(this::editMovie);
        deleteButton.addActionListener(this::deleteMovie);
        scarinessButton.addActionListener(this::showScariness);
        refreshButton.addActionListener(e -> loadMovies());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(scarinessButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMovies();

        setVisible(true);
    }

    /**
     * Loads all movies from the database and displays them in the JTable.
     * <p>
     * Clears the existing table content before reloading data.
     * </p>
     */
    public void loadMovies() {
        tableModel.setRowCount(0);
        ArrayList<Movie> movies = db.getAllMovies();
        for (Movie m : movies) {
            tableModel.addRow(new Object[]{
                    m.getId(), m.getTitle(), m.getYear(), m.getDirector(),
                    m.getRating(), m.getRuntimeMinutes(), m.getVotes(),
                    m.isWatched() ? "Yes" : "No"
            });
        }
    }

    /**
     * Retrieves the currently selected movie from the table.
     *
     * @return the {@link Movie} object corresponding to the selected row, or {@code null} if none is selected.
     */
    private Movie getSelectedMovie() {
        int row = movieTable.getSelectedRow();
        if (row == -1) return null;
        return db.getMovieById((int) tableModel.getValueAt(row, 0));
    }

    /**
     * Opens a dialog to add a new movie to the database.
     *
     * @param e the {@link ActionEvent} triggered by the button click.
     */
    private void addMovie(ActionEvent e) {
        Movie newMovie = MovieDialogGUI.showAddDialog(this);
        if (newMovie != null) {
            db.addMovie(newMovie);
            loadMovies();
        }
    }

    /**
     * Opens a dialog to edit the selected movie.
     *
     * @param e the {@link ActionEvent} triggered by the button click.
     */
    private void editMovie(ActionEvent e) {
        Movie selected = getSelectedMovie();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit.");
            return;
        }
        Movie updated = MovieDialogGUI.showEditDialog(this, selected);
        if (updated != null) {
            db.updateMovie(updated);
            loadMovies();
        }
    }

    /**
     * Deletes the selected movie from the database after confirmation.
     *
     * @param e the {@link ActionEvent} triggered by the button click.
     */
    private void deleteMovie(ActionEvent e) {
        Movie selected = getSelectedMovie();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete \"" + selected.getTitle() + "\"?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            db.deleteMovie(selected.getId());
            loadMovies();
        }
    }

    /**
     * Displays the scariness score for the selected movie.
     *
     * @param e the {@link ActionEvent} triggered by the button click.
     */
    private void showScariness(ActionEvent e) {
        Movie selected = getSelectedMovie();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a movie first.");
            return;
        }
        MovieDialogGUI.showScarinessDialog(this, selected);
    }
}
