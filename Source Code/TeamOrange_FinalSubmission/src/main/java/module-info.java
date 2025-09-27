module org.oosd.teamorange_finalsubmission {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    // Third-party UI
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // JSON (Jackson)
    requires com.fasterxml.jackson.databind;   // sufficient; transitively brings core+annotations

    // Gson
    requires com.google.gson;

    // JetBrains annotations
    requires static org.jetbrains.annotations;

    exports org.oosd.teamorange_finalsubmission;
    opens org.oosd.teamorange_finalsubmission to javafx.fxml, com.fasterxml.jackson.databind;
}
