module com.example.java1_2022_kub0679 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.java1_2022_kub0679 to javafx.fxml;
    exports com.example.java1_2022_kub0679;
}