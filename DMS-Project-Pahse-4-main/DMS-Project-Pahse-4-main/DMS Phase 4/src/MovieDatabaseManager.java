import java.sql.*;
import java.util.ArrayList;

/**
 * Handles all database operations (CRUD) for {@link Movie} objects.
 * <p>
 * This class provides methods to interact with a MySQL database that stores movie data.
 * It supports retrieving, adding, updating, and deleting movies using standard
 * SQL queries via JDBC. Each operation uses {@link PreparedStatement} to prevent
 * SQL injection and ensure secure database access.
 * </p>
 *
 * <p><b>Role in the System:</b></p>
 * Acts as the data access layer in the Data Management System (DMS), abstracting
 * all database communication for the GUI and other application layers.
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *     <li>Requires an active {@link Connection} object (usually provided by {@link DBConnectionDialog}).</li>
 *     <li>Works closely with the {@link Movie} class to represent individual records.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * Connection conn = DBConnectionDialog.showDialog(null);
 * if (conn != null) {
 *     MovieDatabaseManager db = new MovieDatabaseManager(conn);
 *     ArrayList<Movie> allMovies = db.getAllMovies();
 * }
 * }</pre>
 *
 * @author YourName
 * @version 1.0
 */
public class MovieDatabaseManager {

    /** Active database connection used for executing SQL statements. */
    private Connection conn;

    /**
     * Constructs a new {@code MovieDatabaseManager} using an existing database connection.
     *
     * @param connection an active {@link Connection} object for communicating with the database
     */
    public MovieDatabaseManager(Connection connection) {
        this.conn = connection;
    }

    // ==================== CRUD OPERATIONS ====================

    /**
     * Retrieves all movies from the database.
     *
     * @return a list of all {@link Movie} records found in the database; an empty list if none exist
     */
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getString("director"),
                        rs.getDouble("rating"),
                        rs.getInt("runtimeMinutes"),
                        rs.getInt("votes"),
                        rs.getBoolean("watched")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * Retrieves a single {@link Movie} by its unique database ID.
     *
     * @param id the unique identifier of the movie
     * @return a {@link Movie} object if found, or {@code null} if no record matches the ID
     */
    public Movie getMovieById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getString("director"),
                        rs.getDouble("rating"),
                        rs.getInt("runtimeMinutes"),
                        rs.getInt("votes"),
                        rs.getBoolean("watched")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a new {@link Movie} into the database.
     *
     * @param m the {@link Movie} object to insert
     */
    public void addMovie(Movie m) {
        String sql = "INSERT INTO movies (title, year, director, rating, runtimeMinutes, votes, watched) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getTitle());
            stmt.setInt(2, m.getYear());
            stmt.setString(3, m.getDirector());
            stmt.setDouble(4, m.getRating());
            stmt.setInt(5, m.getRuntimeMinutes());
            stmt.setInt(6, m.getVotes());
            stmt.setBoolean(7, m.isWatched());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing {@link Movie} record in the database.
     *
     * @param m the {@link Movie} object containing updated information
     */
    public void updateMovie(Movie m) {
        String sql = "UPDATE movies SET title = ?, year = ?, director = ?, rating = ?, runtimeMinutes = ?, votes = ?, watched = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getTitle());
            stmt.setInt(2, m.getYear());
            stmt.setString(3, m.getDirector());
            stmt.setDouble(4, m.getRating());
            stmt.setInt(5, m.getRuntimeMinutes());
            stmt.setInt(6, m.getVotes());
            stmt.setBoolean(7, m.isWatched());
            stmt.setInt(8, m.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a movie record from the database by its unique ID.
     *
     * @param id the unique identifier of the movie to delete
     */
    public void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
