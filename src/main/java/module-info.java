module com.inventaris {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.inventaris to javafx.fxml;
    opens com.inventaris.controller to javafx.fxml;
    opens com.inventaris.model to javafx.base;
<<<<<<< HEAD


=======
    
>>>>>>> 1dc58fa8653475ca701ed572a7fef80313f8a236
    exports com.inventaris;
}
