module Sol{

    requires javafx.base;
    requires javafx.controls;
    requires java.sql;

    opens jfx to javafx.graphics;
}