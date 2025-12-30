module com.inventaris{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.inventaris to javafx.fxml;
    opens com.inventaris.controller to javafx.fxml;
    opens com.inventaris.model to javafx.base;
    
    exports com.inventaris;
}
