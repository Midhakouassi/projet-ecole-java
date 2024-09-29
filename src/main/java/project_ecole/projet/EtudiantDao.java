package project_ecole.projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtudiantDao {
    private static final Logger logger = Logger.getLogger(EtudiantDao.class.getName());
	
    public Etudiant getEtudiant(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Etudiant etudiant = new Etudiant(); // Créer un objet étudiant vide pour remplir plus tard

        try {
            connection = DbConnexion.getDBConnexion(); // Ouvrir une connexion à la base de données
            connection.setAutoCommit(false);  // Désactiver l'auto-commit pour contrôler la transaction manuellement
            
            // Première requête pour récupérer les informations générales de l'étudiant
            String query = "SELECT * FROM Etudiant where Etudiant.id_etud = ?"; 
            
            // Deuxième requête pour récupérer les examens et notes de l'étudiant
            String query1 = "SELECT etu.id_etud, etu.nom_etud, etu.prenoms_etud, "
                    + "etu.date_naissance_etud, etu.ecole_etud, exam.nom_exam, R.note_exam "
                    + "FROM Etudiant etu "
                    + "INNER JOIN resultat R ON etu.id_etud = R.Etudiant "
                    + "INNER JOIN Examen exam ON R.Examen = exam.id_exam "
                    + "WHERE etu.id_etud = ?";

            // Exécuter la première requête pour récupérer les informations de l'étudiant
            statement = connection.prepareStatement(query);
            statement.setString(1, id);  // Remplacer le ? par l'identifiant de l'étudiant
            ResultSet resultSet = statement.executeQuery();  // Obtenir les résultats de la requête

            // Vérifier si un étudiant avec cet ID existe
            if (resultSet.next()) {
                // Exécuter la seconde requête pour récupérer les examens et notes
                statement = connection.prepareStatement(query1);
                statement.setString(1, id);  // Utiliser à nouveau l'ID de l'étudiant
                ResultSet resultSet1 = statement.executeQuery();
                
                int i = 0;
                // Parcourir les résultats des examens et notes
                while (resultSet1.next()) {
                    if (i == 0) {  // Si c'est le premier résultat, remplir les infos générales de l'étudiant
                        etudiant.setId(resultSet1.getInt(1));             
                        etudiant.setNom(resultSet1.getString(2));          
                        etudiant.setPrenoms(resultSet1.getString(3));      
                        etudiant.setDateNaissance(resultSet1.getString(4)); 
                        etudiant.setEcole(resultSet1.getString(5)); 
                    }
                    // Ajouter chaque note sous la forme "matière:note"
                    etudiant.getNotes().add(resultSet1.getString(6) + ":" + resultSet1.getString(7));
                    i++;  // Incrémenter pour marquer qu'on a déjà rempli les informations de base
                }
            } else {
                return null;  // Retourner null si l'étudiant n'existe pas
            }

        } catch (SQLException exception) {
            // Si une erreur SQL se produit, l'enregistrer dans les logs
            logger.log(Level.SEVERE, exception.getMessage());
        } finally {
            // Fermer la déclaration et la connexion pour libérer les ressources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return etudiant;  // Retourner l'objet étudiant avec ses informations et ses notes
    }
}
