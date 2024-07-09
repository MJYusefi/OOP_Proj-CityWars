module untitled {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires jfiglet;

    exports View;
    exports Model;
    exports Controller;
    opens View to javafx.fxml;
}