package project_ecole.projet;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Etudiant {
	private StringProperty  nom;
	private StringProperty prenoms;
	private StringProperty dateNaissance;
	private StringProperty ecole;
	private IntegerProperty id;
	private ListProperty<String> notes;
	
	public void setNom(String value) {
		nomProperty().set(value);
	}
	public String getNom() {
		return nomProperty().get();
	}
	public StringProperty nomProperty() {
		if(nom == null) nom = new SimpleStringProperty(this, "nom");
		return nom;
	}
	
	public void setPrenoms(String value) {
		prenomsProperty().set(value);
	}
	public String getPrenoms() {
		return prenomsProperty().get();
	}
	public StringProperty prenomsProperty() {
		if(prenoms == null) prenoms = new SimpleStringProperty(this, "prenoms");
		return prenoms;
	}
	
	public void setDateNaissance(String value) {
		nomProperty().set(value);
	}
	public String getDateNaissance() {
		return nomProperty().get();
	}
	public StringProperty dateNaissanceProperty() {
		if(dateNaissance == null) dateNaissance = new SimpleStringProperty(this, "dateNaissance");
		return dateNaissance;
	}
	
	public void setEcole(String value) {
		ecoleProperty().set(value);
	}
	
	public String getEcole() {
		return ecoleProperty().get();
	}
	
	public StringProperty ecoleProperty() {
		if(ecole == null) ecole = new SimpleStringProperty(this, "ecole");
		return ecole;
	}
	
	public void setId(int value) {
		iDProperty().set(value);
	}
	
	public int getId() {
		return iDProperty().get();
	}
	
	public IntegerProperty iDProperty() {
		if(id == null) id = new SimpleIntegerProperty(this, "id");
		return id;
	}
	
	public void setNotes(ObservableList<String> notes) {
        notesProperty().set(notes);
    }
	
	public ObservableList<String> getNotes(){
		return notesProperty().get();
	}
	
	 public ListProperty<String> notesProperty() {
	        if (notes == null) {
	            notes = new SimpleListProperty<>(this, "notes", FXCollections.observableArrayList());
	        }
	        return notes;
	    }
}
