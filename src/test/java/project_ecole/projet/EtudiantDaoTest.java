package project_ecole.projet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EtudiantDaoTest {

    private EtudiantDao etudiantDao;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws Exception {
        etudiantDao = new EtudiantDao();
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Simulation de la connexion à la base de données
        DbConnexion.setDBConnexion(connection);
    }

    @Test
    public void testGetEtudiant_Existe_ShouldRetournerEtudiant() throws Exception {
        String idEtudiant = "1";
        
        // Simulation du comportement de la base de données
        when(connection.prepareStatement(any())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Doe");
        when(resultSet.getString(3)).thenReturn("John");
        when(resultSet.getString(4)).thenReturn("2000-01-01");
        when(resultSet.getString(5)).thenReturn("École A");
        when(resultSet.getString(6)).thenReturn("Math");
        when(resultSet.getString(7)).thenReturn("95");

        Etudiant etudiant = etudiantDao.getEtudiant(idEtudiant);

        // Assertions
        assertThat(etudiant).isNotNull();
        assertThat(etudiant.getId()).isEqualTo(1);
        assertThat(etudiant.getNom()).isEqualTo("Doe");
        assertThat(etudiant.getPrenoms()).isEqualTo("John");
        assertThat(etudiant.getNotes()).contains("Math:95");
    }

    @Test
    public void testGetEtudiant_NExistePas_ShouldRetournerNull() throws Exception {
        String idEtudiant = "2";

        when(connection.prepareStatement(any())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Etudiant etudiant = etudiantDao.getEtudiant(idEtudiant);

        assertThat(etudiant).isNull();
    }
}
