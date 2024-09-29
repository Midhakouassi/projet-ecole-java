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
		Etudiant etudiant = new Etudiant();
		try {
			connection = DbConnexion.getDBConnexion();
			connection.setAutoCommit(false);
			String query = "SELECT * FROM Etudiant where Etudiant.id_etud = ?";
			String query1 = "SELECT etu.id_etud, etu.nom_etud, etu.prenoms_etud, "
			        + "etu.date_naissance_etud, etu.ecole_etud, exam.nom_exam, R.note_exam "
			        + "FROM Etudiant etu "
			        + "INNER JOIN resultat R ON etu.id_etud = R.Etudiant "
			        + "INNER JOIN Examen exam ON R.Examen = exam.id_exam "
			        + "WHERE etu.id_etud = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
            	statement = connection.prepareStatement(query1);
            	statement.setString(1, id);
            	ResultSet resultSet1 = statement.executeQuery();
            	
            	int i = 0;
            	while (resultSet1.next()) {
            		if(i == 0) {
            			etudiant.setId(resultSet1.getInt(1));             
                        etudiant.setNom(resultSet1.getString(2));          
                        etudiant.setPrenoms(resultSet1.getString(3));      
                        etudiant.setDateNaissance(resultSet1.getString(4)); 
                        etudiant.setEcole(resultSet1.getString(5)); 
            			etudiant.getNotes().add(resultSet1.getString(6) + ":" + resultSet1.getString(7));
            		}else {
            			etudiant.getNotes().add(resultSet1.getString(6) + ":" + resultSet1.getString(7));  
            		}
            		i++;
                }
            }else {
            	return null;
            }
			
		}catch(SQLException exception) {
			logger.log(Level.SEVERE, exception.getMessage());
		}finally {
            if (null != statement) {
                statement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
		return etudiant;
	}
}
