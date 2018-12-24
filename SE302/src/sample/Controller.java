package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;


public class Controller {

    public static ObservableList<String> observableCollectionList = FXCollections.observableArrayList(); //Collectionların listede tutulması için
    public DatabaseOperations databaseOperations = new DatabaseOperations();
    @FXML
    private BorderPane mainPanel;
    @FXML
    private ListView<String> collectionListView;

    @FXML
    public void initialize(){
        observableCollectionList.setAll(databaseOperations.takeAllTableName());
        collectionListView.setItems(observableCollectionList);
    }
    @FXML
    public void showAddCollectionDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add new Collection");
        dialog.setHeaderText(null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("collectionDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

        } else {
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void showEditCollectionDialog() {

        /*
        Seçilen koleksiyonun üzerinde edit yapılacak (ekstra bir koleksiyon oluşturmadan).
        Aşağıdaki kodlar showAddCollection methoduyla aynı bunu edite göre düzenleyin!
         */

        System.out.println("Collection eklemeye ait Dialog Pane ayarlanacak");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit collection");
        dialog.setHeaderText(null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("collectionDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /*
            Editlemeye ait kodlar
             */

        } else {
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void showAddItemDialog() {
        /*
        Koleksiyon tarafında aldığımız fieldlar burada doldurulacak.
        Item eklemeye ait dialog pane açılacak textfielda yazılan değerler alınacak
        Buralar tamamen databaseden yapılacak
         */

        System.out.println("Item eklemeye ait Dialog Pane ayarlanacak");
    }

    @FXML
    public void showEditItemDialog () {
        System.out.println("Edit aşaması");
        /*
        Seçilen iteme ait editleme (Database tarafı)
         */
        }
    }

