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
         

        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void showEditCollectionDialog() {

        System.out.println("Collection editlemeye ait Dialog Pane ayarlanacak");
    }

    public void showAddItemDialog() {
        System.out.println("Belirtilen collectiona ait item ekleme işleme gerçekleştirilecek.");


    }
    public void showEditItemDialog(){
        System.out.println("Belirtilen collectiona ait item editleme işleme gerçekleştirilecek.");

    }


}
