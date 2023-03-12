package repair;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import repair.controller.repairController;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);

        FXMLLoader loader = new FXMLLoader();

        repairController controller = new repairController();

        loader.setController(controller);

        Parent root = loader.load(getClass().getResource("/repair/view/repairView.fxml"));

        Image image = new Image("repair/assets/img/ico.png");

        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Repair Management");

        Scene scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add("repair/view/css/repairView.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
