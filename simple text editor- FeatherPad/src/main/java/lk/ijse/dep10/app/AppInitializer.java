package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.app.controller.EditorSceneController;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Untitled Document");

        URL fxmlFile = this.getClass().getResource("/view/EditorScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane root = fxmlLoader.load();

        EditorSceneController ctrl = fxmlLoader.getController();
        ctrl.initData(primaryStage);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setWidth(650);
        primaryStage.setHeight(450);
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
}
