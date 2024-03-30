module com.fsb.linkedin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;
    requires org.apache.pdfbox;


    opens com.fsb.linkedin.utils to javafx.fxml;
    opens com.fsb.linkedin to javafx.fxml;
    exports com.fsb.linkedin;
    exports com.fsb.linkedin.entities;
    exports com.fsb.linkedin.utils;
    exports com.fsb.linkedin.controllers.login;
    opens com.fsb.linkedin.controllers.login to javafx.fxml;
    exports com.fsb.linkedin.controllers.home;
    opens com.fsb.linkedin.controllers.home to javafx.fxml;
    exports com.fsb.linkedin.controllers.signup;
    opens com.fsb.linkedin.controllers.signup to javafx.fxml;


}