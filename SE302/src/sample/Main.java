package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Collection App");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
        TextField textField=new TextField();
        textField.setText("search");

    }




    public static void main(String[] args) {
        launch(args);
    }
}


