module com.fsb.linkedin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens com.fsb.linkedin.entities to javafx.fxml;
    opens com.fsb.linkedin.utils to javafx.fxml;
    opens com.fsb.linkedin to javafx.fxml;
    exports com.fsb.linkedin;
    exports com.fsb.linkedin.entities;
    exports com.fsb.linkedin.utils;

}