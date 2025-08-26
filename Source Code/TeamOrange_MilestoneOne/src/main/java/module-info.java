module org.oosd.teamorange_milestoneone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.base;

    opens org.oosd.teamorange_milestoneone to javafx.fxml;
    exports org.oosd.teamorange_milestoneone;
    exports org.oosd.teamorange_milestoneone.application.views.splash;
    opens org.oosd.teamorange_milestoneone.application.views.splash to javafx.fxml;
}