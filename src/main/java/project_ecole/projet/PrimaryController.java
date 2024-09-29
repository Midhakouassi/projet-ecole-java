package project_ecole.projet;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import java.util.logging.Logger;

public class PrimaryController {
	private static final Logger logger = Logger.getLogger(PrimaryController.class.getName());
    private final EtudiantDao studentDao = new EtudiantDao();
    Etudiant etudiant;
    final int NB_MATIERE = 5;
	
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
    private void switchToSecondary() throws SQLException {
    	 String etudiantId = idInput.getText().trim();
    	 etudiant = studentDao.getEtudiant(etudiantId);
        
    	 if(etudiant != null) {
    		 Integer totalNotes = 0;
    		 for(String note: etudiant.getNotes()) {
    			String[] parts = note.split(":");
    			totalNotes += Integer.parseInt(parts[1]);
    		 }
    		 
    		 queryPane.setManaged(false);
			 queryPane.setVisible(false);
			 
			 resultPane.setManaged(true);
			 resultPane.setVisible(true);
    		 
    		 if(totalNotes >= (NB_MATIERE * 5)) {
    			 successIcon.setManaged(true);
    			 successIcon.setVisible(true);
    			 
    			 resultText.setText("Félicitation "+etudiant.getNom()+" "+etudiant.getPrenoms()+" avec "+totalNotes);
    		 }else {
    			 failIcon.setManaged(true);
    			 failIcon.setVisible(true);
    			 
    			 resultText.setText("Echec "+etudiant.getNom()+" "+etudiant.getPrenoms()+" avec "+totalNotes);

    		 }
    	 }else {
    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	        alert.setTitle("Erreur");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Le matricule entré ne correspond à aucun étudiant " + etudiant.getNom()+etudiant.getDateNaissance()+etudiant.getEcole()+etudiant.getId()+etudiant.getPrenoms());
    	        alert.showAndWait();
    	 }
        
    }
}
