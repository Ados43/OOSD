module org.oosd.teamorange_finalsubmission {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    // Third-party UI library's
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // JSON (Jackson) for ExternalClient
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    // (Optional) Gson — only keep if you still use it somewhere
    requires com.google.gson;

    exports org.oosd.teamorange_finalsubmission;
    opens org.oosd.teamorange_finalsubmission to javafx.fxml, com.fasterxml.jackson.databind;
}