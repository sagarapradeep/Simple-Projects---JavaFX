package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.app.control.SecondViewController;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        /*primary stage operations*/
        URL fxmlFileMain = getClass().getResource("/view/MainView.fxml");
        FXMLLoader fxmlLoaderMain = new FXMLLoader(fxmlFileMain);
        AnchorPane root = fxmlLoaderMain.load();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.centerOnScreen();


        /*passing main scene to second view */



    }
}
