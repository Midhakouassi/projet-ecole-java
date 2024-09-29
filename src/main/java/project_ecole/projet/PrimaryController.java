package project_ecole.projet;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PrimaryController {
	private static final Logger logger = Logger.getLogger(PrimaryController.class.getName());
    private final EtudiantDao etudiantDao = new EtudiantDao();
    Etudiant etudiant;
    final int NB_EXAMEN = 5;
    Integer totalNotes =0;
	@FXML 
	public TextField idInput;
	
	@FXML
	public ImageView successIcon;
	
	@FXML
	public ImageView failIcon;
	
	@FXML
	public Label resultText;
	
	@FXML
	public Pane resultPane;
	
	@FXML
	public Pane queryPane;
	
	@FXML
	public Button submitButton;
	@FXML
	public Label fullnameText;
	
	@FXML
	public Label bornYearText;
	
	@FXML
	public Label schoolText;
	
	@FXML
public Label idText;
	
	@FXML
	public VBox tableExamenBox;

	@FXML
	public Label totalNotesText;
	
	@FXML
	public Pane detailsPane;
	
	@FXML
	public ImageView confetti;
	
	@FXML
	public Label averageText;
	
    @FXML
    protected void switchToSecondary() throws SQLException {
    	 String etudiantId = idInput.getText().trim();
    	 etudiant = etudiantDao.getEtudiant(etudiantId);
        
    	 if(etudiant != null) {
    	
    		 for(String note: etudiant.getNotes()) {
    			String[] parts = note.split(":");
    			totalNotes += Integer.parseInt(parts[1]);
    		 }
    		 
    		 queryPane.setManaged(false);
			 queryPane.setVisible(false);
			 
			 resultPane.setManaged(true);
			 resultPane.setVisible(true);
    		 
    		 if(totalNotes >= (NB_EXAMEN * 10)) {
    			 successIcon.setManaged(true);
    			 successIcon.setVisible(true);
    			 confetti.setManaged(true);
    			 confetti.setVisible(true);
    			 resultText.setText("Congratulation "+etudiant.getNom()+" "+etudiant.getPrenoms());
    			 
    		 }else {
    			 failIcon.setManaged(true);
    			 failIcon.setVisible(true);
    			 
    			 resultText.setText("Failed "+etudiant.getNom()+" "+etudiant.getPrenoms());
    			
    		 }
    	 }else {
    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	        alert.setTitle("Error");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Error with id " + etudiantId);
    	        alert.showAndWait();
    	 }
        
    }
    
    
    @FXML 
    protected void revealExamDetails() throws SQLException{
		resultPane.setManaged(false);
		resultPane.setVisible(false);
		 
		detailsPane.setVisible(true);
		 
    	fullnameText.setText("Fullname: " + etudiant.getNom() + " " + etudiant.getPrenoms());
        bornYearText.setText("Born year: " + etudiant.getDateNaissance());
        schoolText.setText("School : " + etudiant.getEcole());
        idText.setText("Id : " + etudiant.getId());
        
        ExamenDao examenDao = new ExamenDao();
        List<String> exams = examenDao.getAllExamen();
        ExamenService examService = new ExamenService();
        Map<String, String> examNoteMap = examService.mapExamsToNotes(exams, etudiant.getNotes());
        for (Map.Entry<String, String> entry : examNoteMap.entrySet()) {
            Label label = new Label(entry.getKey().toUpperCase() + ": " + entry.getValue());
            label.setStyle("-fx-font-family: 'Consolas';" +     
                    "-fx-font-size: 10px;" +        
                    "-fx-font-weight: bold;");
            tableExamenBox.getChildren().add(label);
        }
       
        
        totalNotesText.setText("Notes summary : " + totalNotes + "/" + (NB_EXAMEN * 20));
        averageText.setText("Average : " + (double)(totalNotes / NB_EXAMEN));
    }
}
