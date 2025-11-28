module org.example.retofrancisco2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.desktop;


    opens org.example.retofrancisco2 to javafx.fxml;
    exports org.example.retofrancisco2;
    exports org.example.retofrancisco2.controllers;
    opens org.example.retofrancisco2.controllers to javafx.fxml;
    opens org.example.retofrancisco2.copias;
    opens org.example.retofrancisco2.peliculas;
    opens org.example.retofrancisco2.usuarios;
}