package lk.ijse.dep10.ap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/Mp3Player.fxml"));
        AnchorPane root = fxmlLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Mp3 Player 1.0.0");
        primaryStage.setResizable(false);
        primaryStage.setWidth(400);
        primaryStage.setHeight(500);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
