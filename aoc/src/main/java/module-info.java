module beer.dacelo.dev.aoq2023 {
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens beer.dacelo.dev.aoq2023 to javafx.fxml;
    exports beer.dacelo.dev.aoq2023;
}
