package project_ecole.projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamenDao { 

    public List<String> getAllExamen() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Examen exams = new Examen();// Objet contenant la liste des examens
        List<String> allExamen = new ArrayList();

        try {
            connection = DbConnexion.getDBConnexion(); // Connexion à la base de données
            connection.setAutoCommit(false); // Désactiver l'auto-commit pour une transaction manuelle

            String query = "SELECT nom_exam FROM Examen"; // Requête SQL pour récupérer les noms d'examens
            statement = connection.prepareStatement(query); // Préparer la requête
            ResultSet resultSet = statement.executeQuery(); // Exécuter la requête

            // Récupérer les résultats et les ajouter à la liste des examens
            while (resultSet.next()) {
            	allExamen.add(resultSet.getString(1));
            }

        } finally {
            // Fermer la déclaration SQL
            if (statement != null) {
                statement.close();
            }
            // Fermer la connexion
            if (connection != null) {
                connection.close();
            }
        }

        return allExamen; // Retourner l'objet Exams avec les résultats
    }

    public int retrieveExamNum() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int examCount = 0; // Nombre d'examens récupérés

        try {
            connection = DbConnexion.getDBConnexion(); // Connexion à la base de données
            connection.setAutoCommit(false); // Désactiver l'auto-commit

            String query = "SELECT COUNT(*) FROM Examen"; // Requête SQL pour compter les examens
            statement = connection.prepareStatement(query); // Préparer la requête
            ResultSet resultSet = statement.executeQuery(); // Exécuter la requête

            // Récupérer le nombre d'examens
            if (resultSet.next()) {
                examCount = resultSet.getInt(1); // Récupérer le résultat du COUNT
            }

        } finally {
            // Fermer la déclaration SQL
            if (statement != null) {
                statement.close();
            }
            // Fermer la connexion
            if (connection != null) {
                connection.close();
            }
        }

        return examCount; // Retourner le nombre d'examens
    }
}
