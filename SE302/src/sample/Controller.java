package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Optional;

public class Controller {


    @FXML
    private BorderPane borderPane;


    public void showAddCollectionDialog() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("collectionDialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Colection");
            stage.setScene(new Scene(root1));
            stage.show();
            Dialog<ButtonType> dialog = new Dialog<>();

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void showEditCollectionDialog() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("collectionDialog.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Collection");
            stage.setScene(new Scene(root2));
            stage.show();
            Dialog<ButtonType> dialog = new Dialog<>();

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void showAddItemDialog() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemDialog.fxml"));
            Parent root3 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Item");
            stage.setScene(new Scene(root3));
            stage.show();
            Dialog<ButtonType> dialog = new Dialog<>();

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }


    }
    public void showEditItemDialog(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemDialog.fxml"));
            Parent root4 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Item");
            stage.setScene(new Scene(root4));
            stage.show();
            Dialog<ButtonType> dialog = new Dialog<>();

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

    }


}
