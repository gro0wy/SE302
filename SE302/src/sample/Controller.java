package sample;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class Controller {

    @FXML
    private BorderPane mainPanel;
    @FXML
    public ListView<String> collectionListView;

    public TableView<ObservableList> tableView = new TableView<>();

    public static String editingItem;
    public static ObservableList<String> observableCollectionList = FXCollections.observableArrayList(); //Collectionların listede tutulması için
    public static ObservableList<ObservableList> data = FXCollections.observableArrayList();
    private DatabaseOperations databaseOperations = new DatabaseOperations();
    private ItemOperations itemOperations = new ItemOperations();

    public static String url = "jdbc:sqlite:CollectionApp.db";

    @FXML
    public void initialize(){

        try {
            observableCollectionList.setAll(databaseOperations.takeAllTableName());
        }catch (Exception e){
            System.out.println("Hata geldi");
        }
        mainPanel.setCenter(tableView);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        collectionListView.setItems(observableCollectionList);

        collectionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    TableViewContent(collectionListView.getSelectionModel().getSelectedItem().toString(), tableView, data);
            }
        });
    }

    @FXML
    public void deleteCollection() {
        if (collectionListView.getSelectionModel().getSelectedItem() != null) {
            databaseOperations.deleteTable(collectionListView.getSelectionModel().getSelectedItem());
            observableCollectionList.setAll(databaseOperations.takeAllTableName());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Please select an item to delete!");
            alert.setContentText("Something went wrong. Try again!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();
        }
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

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();
        initialize();

    }

    @FXML
    public void showEditCollectionDialog() {

        if (collectionListView.getSelectionModel().getSelectedItem() != null) {

            editingItem = collectionListView.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainPanel.getScene().getWindow());
            dialog.setTitle("Edit Collection");
            dialog.setHeaderText(null);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("editCollection.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Select a collection!");
            alert.setContentText("Something went wrong");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();

        }
        initialize();

    }
    @FXML
    public void showAddItemDialog() {
        /*
        Koleksiyon tarafında aldığımız fieldlar burada doldurulacak.
        Item eklemeye ait dialog pane açılacak textfielda yazılan değerler alınacak
        Buralar tamamen databaseden yapılacak
         */

        System.out.println("Item eklemeye ait Dialog Pane ayarlanacak");
        if(collectionListView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Select a collection!");
            alert.setContentText("Something went wrong");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();
        }else{


            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainPanel.getScene().getWindow());
            DialogPane itemDialogPane = new DialogPane();
            dialog.setTitle("Add item -> " +collectionListView.getSelectionModel().getSelectedItem());
            //Bize burdan liste gelmesi gerekiyor (databaseden seçili collectiona göre) column nameleri mesela (Book'a ait BookName, BookPageNumber, BookAuthor bunların
            //bir listede gelmesi lazım
            ArrayList<String> sample = itemOperations.getColumnNames(collectionListView.getSelectionModel().getSelectedItem());
/*
            sample.add("Name");
            sample.add("Age");
            sample.add("page number");
            sample.add("year");
*/
            VBox vBox = new VBox();
            TextField[] textFields = new TextField[sample.size()];
            Label[] labelList = new Label[sample.size()];

            for(int i=0; i<sample.size(); i++){
                labelList[i] = new Label(sample.get(i));
                textFields[i] = new TextField();

                vBox.getChildren().addAll(labelList[i], textFields[i]);
            }

            Button addItem = new Button("Add Item");
            vBox.getChildren().add(addItem);
            vBox.setSpacing(10);

            //User inputlarını bir yerde tutuyoruz
            try {
                addItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<String> userInputs = new ArrayList<>();
                        for (int i = 0; i < sample.size(); i++) {
                            userInputs.add(textFields[i].getText());
                        }
                        if (userInputs.contains("")) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Look, a Warning Dialog");
                            alert.setContentText("Something went wrong!");
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));

                            alert.showAndWait();
                        }
                        else {
                            // insert sql methodu çağırılacak ve userInputs List parametre olarak gönderilecek. Kolon sırasına göre inputlar insert edilecek.
                            //methodsql(userInputs); //bu sınıf yazılırken içinde try catch kullanılmayacak hata bu sınıfı çağıran sınafa throw edilecek
                            itemOperations.insertItem(collectionListView.getSelectionModel().getSelectedItem(),userInputs);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Item Added");
                            alert.setHeaderText("Item added to " +collectionListView.getSelectionModel().getSelectedItem());
                            alert.setContentText("SUCCESSFUL!");
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image(this.getClass().getResource("/icons/confirm.png").toString()));
                            alert.showAndWait();

                            for (int i = 0; i < sample.size(); i++) {
                                textFields[i].clear();

                            }
                        }
                    }

                });

            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, a Warning Dialog");
                alert.setContentText("Something went wrong!");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));

                alert.showAndWait();

            }

            itemDialogPane.setContent(vBox);
            dialog.getDialogPane().setContent(itemDialogPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.showAndWait();

        }

    }

    @FXML
    public void showEditItemDialog () {
        System.out.println("Edit aşaması");
        /*
        Seçilen iteme ait editleme (Database tarafı)
         */
        }

    public void TableViewContent (String tableName, TableView table, ObservableList<ObservableList> tableList ) {

        table.getColumns().clear();
        tableList.clear();
        String sql = "Select * From '"+tableName+"' ";
        try ( Connection conn = DriverManager.getConnection(url);
              Statement  stmt = conn.createStatement();
              ResultSet  rs = stmt.executeQuery(sql)) {


            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
                //    System.out.println("Column [" + i + "] ");
            }
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //   System.out.println("Row [1] added " + row);
                tableList.add(row);

            }

            table.setItems(tableList);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error on Building Data");
        }

    }
    }

