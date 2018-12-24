package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;


public class CollectionDialog {
    @FXML
    private TextField collectionField;
    @FXML
    private TextField collectionAttField;
    @FXML
    private ListView<String> myListView;
    @FXML
    private ComboBox<String> comboBox;

    public static String collectionFieldName;

    public static ObservableList<String> observableFieldList = FXCollections.observableArrayList(); //Fieldların listede tutulması



    @FXML
    public void initialize() {

        myListView.setItems(observableFieldList);
        collectionField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                collectionFieldName = newValue;
            }
        });
    }


    //Fields added to list view in the dialog pane
    @FXML
    public void addFieldsToListView(){

        if(collectionAttField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Field Error");
            alert.setContentText("Field cannot be empty");
            alert.showAndWait();
        }
        else{
            if(observableFieldList.contains(comboBox.getSelectionModel().getSelectedItem().toUpperCase() + "-" + collectionAttField.getText().toUpperCase())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Field Error");
            alert.setContentText("Field already exist. Try again");
            alert.showAndWait();
        } else {
            myListView.getItems().add(comboBox.getSelectionModel().getSelectedItem().toUpperCase() + "-" + collectionAttField.getText().toUpperCase());
            collectionAttField.clear();
        }
        }

        /*
        ObservableArrayList kullanılarak listviewdaki işlemler için takip sağlanacak. YAPILDI
        Delete veya add durumlarında ObservableArrayList yardımcı olacak YAPILDI
        Alınan fieldlar belirli koleksiyona ait alanada tutulması gerekiyor.
        Mesela Book collectiona ait -> Name, Page Number, Author gibi alanlar sadece Book collectionuna ait. YAPILDI
         */
        /*
        Eğer aynı field name varsa hata döndür(contains)  YAPILDI
         */

    }
    //Fields deleted from list view in the dialog pane
    @FXML
    public void deleteFieldsFromListView(){
        if(myListView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Choose a field to delete");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Delete " + myListView.getSelectionModel().getSelectedItem());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                observableFieldList.remove(myListView.getSelectionModel().getSelectedItem());

            } else {
                System.out.println("User pressed CANCEL");
            }

        }
    }
}