package project_ecole.projet;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Examen {
    private ListProperty<String> listeExamens;

    public void setExamensList(ObservableList<String> listeExamens) {
    	listeExamensProperty().set(listeExamens);
    }

    public ObservableList<String> getListeExamens() {
        return listeExamensProperty().get();
    }

    public ListProperty<String> listeExamensProperty() {
        if (listeExamens == null) {
        	listeExamens = new SimpleListProperty<>(this, "listeExamens", FXCollections.observableArrayList());
        }
        return listeExamens;
    }
}
