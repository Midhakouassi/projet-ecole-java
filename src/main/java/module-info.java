module project_ecole.projet {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;

    opens project_ecole.projet to javafx.fxml;
    exports project_ecole.projet;
}
