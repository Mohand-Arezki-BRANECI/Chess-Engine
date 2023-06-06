module com.example.jfxchess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.jfxchess to javafx.fxml;
    exports com.example.jfxchess;
    exports com.example.jfxchess.gui;
    opens com.example.jfxchess.gui to javafx.fxml;
}