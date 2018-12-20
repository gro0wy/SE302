package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class CollectionDialog {
    @FXML
    private TextField collectionField;
    @FXML
    private TextField collectionAttField;
    @FXML
    private ComboBox comboBox;
    @FXML
    private ListView<String> myListView;
    @FXML
    private VBox vBox;

    public void addItemsToListView() {
/*
        vBox = new VBox();
*/
        myListView.getItems().add(collectionAttField.getText());
/*        ArrayList<TextField> elements = new ArrayList<>();
        elements.add(collectionAttField);
        elements.add(collectionAttField);

        for (int i = 0; i < elements.size(); i++) {
            printFields.getItems().add(elements.get(i));
            vBox.getChildren().addAll(printFields);

        }*/


    }
}
