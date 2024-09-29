package project_ecole.projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamenDao { 

    public List<String> getAllExamen() throws SQLException {
        Connection connection = null; // Déclaration de la connexion
        PreparedStatement statement = null; // Déclaration de la requête préparée
        List<String> allExamen = new ArrayList<>(); // Liste pour stocker les noms d'examens

        try {
            connection = DbConnexion.getDBConnexion(); // Ouvrir une connexion à la base de données
            connection.setAutoCommit(false); // Désactiver l'auto-commit pour gérer la transaction manuellement

            String query = "SELECT nom_exam FROM Examen"; // Requête pour obtenir les noms d'examens
            statement = connection.prepareStatement(query); // Préparer la requête
            ResultSet resultSet = statement.executeQuery(); // Exécuter la requête et obtenir les résultats

            // Parcourir les résultats et les ajouter à la liste
            while (resultSet.next()) {
                allExamen.add(resultSet.getString(1)); // Ajouter chaque nom d'examen à la liste
            }

        } finally {
            // Fermer la déclaration SQL pour libérer les ressources
            if (statement != null) {
                statement.close();
            }
            // Fermer la connexion à la base de données
            if (connection != null) {
                connection.close();
            }
        }

        return allExamen; // Retourner la liste des noms d'examens
    }

    
     
}
