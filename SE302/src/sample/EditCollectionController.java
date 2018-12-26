package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Optional;

public class EditCollectionController {

    @FXML
    private TextField editCollectionField;
    @FXML
    private TextField editCollectionAttrField;
    @FXML
    private ListView<String> editListView;

    public static ItemOperations fields = new ItemOperations();

    public static ObservableList<String> editedObservableFieldList = FXCollections.observableArrayList();


    @FXML
    public static String editedCollectionFieldName;

    public void initialize() {
        editedCollectionFieldName = Controller.editingItem;

        editCollectionField.setEditable(true);
        editedObservableFieldList.setAll(fields.getColumnNames(Controller.editingItem));
        editListView.setItems(editedObservableFieldList);
        editCollectionField.setText(Controller.editingItem);

            editCollectionField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    editedCollectionFieldName = newValue;
                }
            });
        }


    @FXML
    public void editAddFields() {
        if (editCollectionAttrField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Field Error");
            alert.setContentText("Field cannot be empty");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();
        } else {

            if (editedObservableFieldList.contains(editCollectionAttrField.getText().trim().toUpperCase())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Field Error");
                alert.setContentText("Field already exist. Try again");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
                alert.showAndWait();

            } else {
                editListView.getItems().add(editCollectionAttrField.getText().toUpperCase());
                editCollectionAttrField.clear();
            }
        }
    }

    @FXML
    public void editDeleteFields() {
        if (editListView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Choose a field to delete");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Delete " + editListView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/confirm.png").toString()));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                editedObservableFieldList.remove(editListView.getSelectionModel().getSelectedItem());

            } else {
                System.out.println("User pressed CANCEL");
            }

        }
    }

    @FXML
    public void editCollection() {

        try {
            if ((editCollectionField == null || editedObservableFieldList.isEmpty())) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("All fields must be filled");
                alert.setContentText("Collection name cannot be empty or at least one field needed!");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
                alert.showAndWait();
            } else {
            /*
            callUpdateMethod(CollectionDialog.editedCollectionName, CollectionDialog.editObservableFieldList);

            Bu methodla databasede create yapılacak, içinde tuttuğumuz observableFieldList bizim tabledaki columnlarımız oluyor.
            */


                System.out.println("COLLECTION NAME: " + editedCollectionFieldName);
                System.out.println("FIELDS OF THE COLLECTION: ");
                for (int i = 0; i < editedObservableFieldList.size(); i++) {
                    System.out.println(editedObservableFieldList.get(i));

                }
                //editedObservableFieldList.clear();
                //editCollectionField.clear();

            }
        } catch (Exception e){
            System.out.println(e.getMessage());Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Try again");
            alert.setContentText("Something went wrong!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();

        }
    }
}


