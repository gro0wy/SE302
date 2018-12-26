package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseOperations db = new DatabaseOperations();
        db.createNewDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Collection App");
        primaryStage.setScene(new Scene(root, 1024, 768));
        Image mainIcon = new Image("/icons/LOGO.png");
        primaryStage.getIcons().add(mainIcon);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}


