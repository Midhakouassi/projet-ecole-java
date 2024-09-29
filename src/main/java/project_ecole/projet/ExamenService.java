package project_ecole.projet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamenService {

    // Cette méthode fait correspondre chaque examen avec sa note
    public Map<String, String> mapExamsToNotes(List<String> exams, List<String> notes) {
        // Crée une map pour stocker les matières et les notes (comme un tableau associatif)
        // La clé sera le nom de la matière, et la valeur sera la note
        Map<String, String> examNoteMap = new HashMap<>();

        // Parcourir la liste des notes qui sont sous la forme "matière:note"
        for (String note : notes) {
            // Séparer la chaîne de caractères en deux parties : matière et note
            String[] parts = note.split(":");
            // Si la chaîne contient bien deux parties (matière et note)
            if (parts.length == 2) {
                String matiere = parts[0]; // La matière est avant le ":"
                String noteValue = parts[1]; // La note est après le ":"
                // Ajouter la matière et la note dans la map
                examNoteMap.put(matiere, noteValue);
            }
        }

        // Créer une nouvelle map pour associer les examens avec les notes correspondantes
        Map<String, String> examToNoteMap = new HashMap<>();

        // Parcourir la liste des examens pour leur attribuer la note correspondante
        for (String exam : exams) {
            // Si la matière de l'examen est présente dans la map des notes
            if (examNoteMap.containsKey(exam)) {
                // Associer l'examen avec la note trouvée dans la map
                examToNoteMap.put(exam, examNoteMap.get(exam));
            } else {
                // Si aucune note n'est trouvée pour cet examen, attribuer "N/A" comme valeur par défaut
                examToNoteMap.put(exam, "N/A");
            }
        }

        // Retourner la map qui contient les examens avec leurs notes associées
        return examToNoteMap;
    }
}

