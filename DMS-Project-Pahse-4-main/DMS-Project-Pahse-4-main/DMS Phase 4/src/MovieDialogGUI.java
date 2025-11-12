import javax.swing.*;
import java.awt.*;

/**
 * A dialog window used for adding or editing {@link Movie} objects in the Movie Data Management System.
 * <p>
 * This dialog allows users to input or modify movie details such as title, year, director,
 * rating, runtime, votes, and watched status. It includes data validation and displays
 * appropriate error messages when invalid data is entered.
 * </p>
 *
 * <p><b>Role in the System:</b></p>
 * <ul>
 *     <li>Provides a graphical interface for creating and updating {@link Movie} records.</li>
 *     <li>Communicates with {@link MovieGUI} to return validated {@link Movie} objects.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * Movie newMovie = MovieDialogGUI.showAddDialog(parentFrame);
 * if (newMovie != null) {
 *     db.addMovie(newMovie);
 * }
 * }</pre>
 *
 * @author YourName
 * @version 1.0
 */
public class MovieDialogGUI extends JDialog {

    private JTextField titleField, yearField, directorField, ratingField, runtimeField, votesField;
    private JCheckBox watchedBox;
    private Movie movie;

    /**
     * Constructs a dialog for adding or editing a movie record.
     * <p>
     * This constructor initializes the dialog layout, sets up all input fields,
     * and applies a consistent dark theme to the UI components.
     * </p>
     *
     * @param parent the parent {@link Frame} component, usually the main application window.
     * @param title  the title to display at the top of the dialog (e.g., "Add Movie" or "Edit Movie").
     */
    private MovieDialogGUI(Frame parent, String title) {
        super(parent, title, true);
        setSize(400, 420);
        setLayout(new GridLayout(9, 2, 8, 8));
        setLocationRelativeTo(parent);

        // ===== Elegant Dark Theme Colors =====
        Color background = new Color(43, 43, 43);
        Color panelColor = new Color(58, 58, 58);
        Color textColor = Color.WHITE;
        Color gold = new Color(201, 168, 106);

        getContentPane().setBackground(background);

        // ===== Add Fields =====
        addStyledLabel("Title:", textColor);
        titleField = createTextField(panelColor, textColor); add(titleField);

        addStyledLabel("Year:", textColor);
        yearField = createTextField(panelColor, textColor); add(yearField);

        addStyledLabel("Director:", textColor);
        directorField = createTextField(panelColor, textColor); add(directorField);

        addStyledLabel("Rating (0-10):", textColor);
        ratingField = createTextField(panelColor, textColor); add(ratingField);

        addStyledLabel("Runtime (minutes):", textColor);
        runtimeField = createTextField(panelColor, textColor); add(runtimeField);

        addStyledLabel("Votes:", textColor);
        votesField = createTextField(panelColor, textColor); add(votesField);

        addStyledLabel("Watched:", textColor);
        watchedBox = new JCheckBox();
        watchedBox.setBackground(background);
        watchedBox.setForeground(textColor);
        add(watchedBox);

        // ===== Buttons =====
        JButton confirmBtn = styledButton("Save", gold);
        JButton cancelBtn = styledButton("Cancel", gold);

        confirmBtn.addActionListener(e -> attemptSave());
        cancelBtn.addActionListener(e -> {
            movie = null;
            dispose();
        });

        add(confirmBtn);
        add(cancelBtn);
    }

    /**
     * Creates and adds a styled label to the dialog.
     *
     * @param text      the label text.
     * @param textColor the label text color.
     */
    private void addStyledLabel(String text, Color textColor) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        add(label);
    }

    /**
     * Creates a styled text field with custom background and foreground colors.
     *
     * @param bg the background color.
     * @param fg the text (foreground) color.
     * @return a styled {@link JTextField} component.
     */
    private JTextField createTextField(Color bg, Color fg) {
        JTextField f = new JTextField();
        f.setBackground(bg);
        f.setForeground(fg);
        f.setCaretColor(fg);
        return f;
    }

    /**
     * Creates a styled button with the specified label and color scheme.
     *
     * @param text the button label.
     * @param gold the base color for the button background.
     * @return a styled {@link JButton} component.
     */
    private JButton styledButton(String text, Color gold) {
        JButton btn = new JButton(text);
        btn.setBackground(gold);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        return btn;
    }

    /**
     * Attempts to create a {@link Movie} object from the user input fields.
     * <p>
     * This method validates input data (e.g., numeric ranges, non-empty strings),
     * shows an error message when invalid data is detected, and closes the dialog
     * if the movie was successfully created.
     * </p>
     *
     * @throws IllegalArgumentException if the input fields contain invalid data.
     */
    private void attemptSave() {
        try {
            String title = titleField.getText().trim();
            String director = directorField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            double rating = Double.parseDouble(ratingField.getText().trim());
            int runtime = Integer.parseInt(runtimeField.getText().trim());
            int votes = Integer.parseInt(votesField.getText().trim());
            boolean watched = watchedBox.isSelected();

            int currentYear = java.time.Year.now().getValue();
            if (title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty.");
            if (director.isEmpty()) throw new IllegalArgumentException("Director cannot be empty.");
            if (year < 1888 || year > currentYear) throw new IllegalArgumentException("Year must be between 1888 and " + currentYear + ".");
            if (rating < 0 || rating > 10) throw new IllegalArgumentException("Rating must be between 0 and 10.");
            if (runtime <= 0) throw new IllegalArgumentException("Runtime must be positive.");
            if (votes < 0) throw new IllegalArgumentException("Votes cannot be negative.");

            movie = new Movie(0, title, year, director, rating, runtime, votes, watched);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values for Year, Rating, Runtime, and Votes.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the dialog for adding a new movie.
     *
     * @param parent the parent {@link Frame} window.
     * @return a new {@link Movie} object if the user clicks "Save", or {@code null} if canceled.
     */
    public static Movie showAddDialog(Frame parent) {
        MovieDialogGUI dialog = new MovieDialogGUI(parent, "Add Movie");
        dialog.setVisible(true);
        return dialog.movie;
    }

    /**
     * Displays the dialog for editing an existing movie.
     *
     * @param parent the parent {@link Frame} window.
     * @param m      the {@link Movie} to be edited.
     * @return the updated {@link Movie} object if the user clicks "Save", or {@code null} if canceled.
     */
    public static Movie showEditDialog(Frame parent, Movie m) {
        MovieDialogGUI dialog = new MovieDialogGUI(parent, "Edit Movie");

        dialog.titleField.setText(m.getTitle());
        dialog.yearField.setText(String.valueOf(m.getYear()));
        dialog.directorField.setText(m.getDirector());
        dialog.ratingField.setText(String.valueOf(m.getRating()));
        dialog.runtimeField.setText(String.valueOf(m.getRuntimeMinutes()));
        dialog.votesField.setText(String.valueOf(m.getVotes()));
        dialog.watchedBox.setSelected(m.isWatched());

        dialog.setVisible(true);

        if (dialog.movie != null) {
            dialog.movie.setId(m.getId()); // Preserve original ID
        }
        return dialog.movie;
    }

    /**
     * Displays a popup showing the scariness score of the selected movie.
     *
     * @param parent the parent {@link Frame} window.
     * @param m      the {@link Movie} for which the score will be shown.
     */
    public static void showScarinessDialog(Frame parent, Movie m) {
        double score = m.getScariness();
        JOptionPane.showMessageDialog(parent,
                "Scariness Score for \"" + m.getTitle() + "\":\n\n" + score + " / 10",
                "Scariness Score",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
