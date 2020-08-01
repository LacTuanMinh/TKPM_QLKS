module ql.khachsan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.persistence;
    requires java.naming;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires jbcrypt;
    //requires slf4j.api;
    //requires java.desktop;
    //requires javafx.swing;

    opens ql.khachsan to javafx.fxml;
    opens ql.khachsan.controllers to javafx.fxml;
    opens ql.khachsan.models to org.hibernate.orm.core, javafx.base;

    exports ql.khachsan;
    exports ql.khachsan.controllers;
}