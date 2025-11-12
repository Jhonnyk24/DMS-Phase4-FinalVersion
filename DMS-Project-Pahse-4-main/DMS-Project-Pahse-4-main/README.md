# 🎬 Movie Database System Phase 4

A Java-based Movie Database Management System with a modern GUI, backed by **MySQL**.  
Allows users to **create, read, update, delete (CRUD)** movie records and calculate a **scariness score** for horror movies.  

---

## 📝 Features

- **Elegant GUI:** Dark theme with gold accents, modern buttons, and Nimbus look-and-feel.
- **CRUD Operations:**  
  - Add, edit, delete movies.  
  - Display all movies in a table.  
- **Custom Feature:** Calculates a "scariness score" for each movie based on rating, votes, runtime, and whether it has been watched.  
- **Database Integration:** Connects to MySQL for persistent storage.  
- **Error Handling:** All user inputs are validated; program cannot crash due to bad inputs.   

---

## 🎯 Rubric Coverage

| Criterion | Status |
|-----------|--------|
| Display Menu | ✅ Passed |
| Read Data from Database | ✅ Passed |
| Display Data | ✅ Passed |
| Create Data | ✅ Passed |
| Remove Data | ✅ Passed |
| Update Data | ✅ Passed |
| Custom Feature (Scariness Score) | ✅ Passed |
| Code Commenting & Documentation | ✅ Passed |

---

## 🛠️ Prerequisites

- **Java 17** or higher  
- **MySQL 8.x**  
- **MySQL Connector/J** (JAR included in `/lib`)  
- IDE: IntelliJ IDEA recommended  

---

## ⚡ Installation & Setup

1. Clone the repository:
   ```bash
   git (https://github.com/Jhonnyk24/DMS-Project-Pahse-4.git)
   cd moviedb-system
   
Setup MySQL Database:

CREATE DATABASE moviesdb;
USE moviesdb;

CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    director VARCHAR(255) NOT NULL,
    rating DOUBLE NOT NULL,
    runtimeMinutes INT NOT NULL,
    votes INT NOT NULL,
    watched TINYINT(1) NOT NULL
);
Add MySQL Connector JAR to your project library.

Run Main.java or MainGUI.java to launch the program.
