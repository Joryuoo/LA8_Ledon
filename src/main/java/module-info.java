module com.example.graphsledon {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphsledon to javafx.fxml;
    exports com.example.graphsledon;
}