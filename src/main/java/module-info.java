module com.example.tp2cabinet_medical {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.tp2cabinet_medical to javafx.fxml;
    exports com.example.tp2cabinet_medical;
}