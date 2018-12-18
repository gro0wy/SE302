package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.Optional;

public class Controller {


    @FXML
    private BorderPane borderPane;


    public void showAddCollectionDialog() {

        System.out.println("Collection eklemeye ait Dialog Pane ayarlanacak");
        Dialog<ButtonType> dialog = new Dialog<>();

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
