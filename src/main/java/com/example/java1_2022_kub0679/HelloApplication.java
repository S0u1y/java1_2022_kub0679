/* *
* Game originally made by Vandenn Games.
* Recreated purely for educational purposes.
* */

package com.example.java1_2022_kub0679;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class HelloApplication extends Application {

    private HelloController controller;



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coin Apocalypse");
        stage.setScene(scene);
        stage.show();

        controller = fxmlLoader.getController();
        controller.startGame();

        stage.setOnCloseRequest(event -> {

            controller.endGame();

            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}