# 🎬 Movie Data Management System (DMS)

![Java](https://img.shields.io/badge/Java-8+-blue)  
![License](https://img.shields.io/badge/License-MIT-green)

A **Java-based desktop application** for managing a personal movie database. Users can add, edit, delete, and view movies with a graphical interface. Each movie also includes a **custom “scariness” score** based on rating, votes, runtime, and watched status.

---

## 📌 Features

- Add, edit, and delete movies.
- Store movie metadata: title, year, director, rating, runtime, votes, and watched status.
- Calculate **scariness score** for each movie.
- Interactive GUI with table view and styled buttons.
- Database connectivity via MySQL using JDBC.
- Data validation to prevent invalid input.

---

## 🗂 Project Structure

| Class | Description |
|-------|-------------|
| `Movie` | Represents a movie entity. Stores metadata and computes scariness. Supports CSV import/export. |
| `MovieDatabaseManager` | Handles database operations (CRUD) for movies. Uses JDBC with `PreparedStatement`. |
| `MovieDialogGUI` | Dialog for adding/editing movies. Validates input and communicates with GUI. |
| `MovieGUI` | Main application window. Displays movies in a table with CRUD operations and scariness calculation. |
| `DBConnectionDialog` | Dialog to input database connection details and returns a valid JDBC `Connection`. |
| `Main` | Entry point of the application. Opens connection dialog and launches main GUI. |

---

## ⚙️ Getting Started

### Prerequisites

- Java 8 or higher
- MySQL database (or compatible)
- JDBC driver for MySQL in your classpath

### Database Setup

```sql
CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    director VARCHAR(255),
    rating DOUBLE,
    runtimeMinutes INT,
    votes INT,
    watched BOOLEAN
);
Clone Repository
bash
Copiar código
git clone https://github.com/<your-username>/MovieDMS.git
cd MovieDMS
Run the Application
Open your IDE and import the project.

Compile all .java files.

Run Main.java.

Enter database credentials in the connection dialog.

The main GUI will launch.

🖥 Usage
Add Movie: Click Add Movie, enter details, and save.

Edit Movie: Select a movie, click Edit Movie, modify fields, and save.

Delete Movie: Select a movie, click Delete Movie, and confirm deletion.

Show Scariness: Select a movie and click Show Scariness to see the score.

Refresh Table: Click Refresh to reload data from the database.

🔧 Example Code
java
Copiar código
Movie m = new Movie("Inception", 2010, "Christopher Nolan", 8.8, 148, 2200000, true);
System.out.println(m.prettyPrint());

MovieDatabaseManager db = new MovieDatabaseManager(connection);
db.addMovie(m);

new MovieGUI(db); // Launch GUI
