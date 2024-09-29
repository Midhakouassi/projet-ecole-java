package project_ecole.projet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PrimaryControllerTest {

    @InjectMocks
    private PrimaryController primaryController;

    @Mock
    private EtudiantDao etudiantDao;

    @Mock
    private ExamenDao examenDao;

    @Mock
    private ExamenService examenService;

    // Mocking JavaFX components
    @Mock
    public TextField idInput;
    
    @Mock
    public ImageView successIcon;
    
    @Mock
    public ImageView failIcon;
    
    @Mock
    public Label resultText;
    
    @Mock
    public Pane resultPane;
    
    @Mock
    public Pane queryPane;
    
    @Mock
    public VBox tableExamenBox;
    
    @Mock
    public Label totalNotesText;
    
    @Mock
    public Label fullnameText;
    
    @Mock
    public Label bornYearText;
    
    @Mock
    public Label schoolText;
    
    @Mock
    public Label idText;
    
    @Mock
    public ImageView confetti;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        primaryController.totalNotes = 0; // Reset total notes before each test
    }

    @Test
    public void testSwitchToSecondary_StudentExists_Success() throws SQLException {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setId(1);
        etudiant.setNom("Doe");
        etudiant.setPrenoms("John");
        etudiant.setNotes(Arrays.asList("Math:15", "Science:20", "English:18", "History:10", "Geography:14"));

        when(idInput.getText()).thenReturn("1");
        when(etudiantDao.getEtudiant("1")).thenReturn(etudiant);
        
        // Act
        primaryController.switchToSecondary();

        // Assert
        verify(queryPane).setManaged(false);
        verify(queryPane).setVisible(false);
        verify(resultPane).setManaged(true);
        verify(resultPane).setVisible(true);
        assertTrue(primaryController.totalNotes >= primaryController.NB_EXAMEN * 10);
        verify(successIcon).setManaged(true);
        verify(successIcon).setVisible(true);
        verify(resultText).setText("Congratulation Doe John");
        verify(confetti).setManaged(true);
        verify(confetti).setVisible(true);
    }

    @Test
    public void testSwitchToSecondary_StudentDoesNotExist_ShowsAlert() throws SQLException {
        // Arrange
        when(idInput.getText()).thenReturn("2");
        when(etudiantDao.getEtudiant("2")).thenReturn(null);

        // Act
        primaryController.switchToSecondary();

        // Assert
        verify(queryPane).setManaged(true);
        verify(queryPane).setVisible(true);
        verify(resultPane).setManaged(false);
        verify(resultPane).setVisible(false);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Error with id 2");
        alert.showAndWait();
    }

    @Test
    public void testRevealExamDetails() throws SQLException {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Doe");
        etudiant.setPrenoms("John");
        etudiant.setDateNaissance("2000-01-01");
        etudiant.setEcole("École A");
        etudiant.setId(1);
        etudiant.setNotes(Arrays.asList("Math:15", "Science:20"));

        primaryController.etudiant = etudiant;

        List<String> exams = Arrays.asList("Math", "Science");
        when(examenDao.getAllExamen()).thenReturn(exams);
        when(examenService.mapExamsToNotes(exams, etudiant.getNotes())).thenReturn(Map.of("Math", "15", "Science", "20"));

        // Act
        primaryController.revealExamDetails();

        // Assert
        assertEquals("Fullname: Doe John", primaryController.fullnameText.getText());
        assertEquals("Born year: 2000-01-01", primaryController.bornYearText.getText());
        assertEquals("School : École A", primaryController.schoolText.getText());
        assertEquals("Id : 1", primaryController.idText.getText());
        assertEquals("Notes summary : 15/100", primaryController.totalNotesText.getText());
        assertEquals(2, primaryController.tableExamenBox.getChildren().size());
    }
}
