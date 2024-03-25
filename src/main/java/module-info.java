module com.fsb.linkedin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.fsb.linkedin to javafx.fxml;
    exports com.fsb.linkedin;

}