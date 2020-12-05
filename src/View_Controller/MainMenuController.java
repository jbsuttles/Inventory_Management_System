package View_Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField partSearchField;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField productSearchField;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View_Controller/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void OnActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../View_Controller/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void OnActionDeletePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void OnActionDeleteProduct(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
        }

    }

    @FXML
    void OnActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    @FXML
    void OnActionModifyPart(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View_Controller/ModifyPart.fxml"));
            loader.load();

            ModifyPartController ModifyPartController = loader.getController();
            ModifyPartController.setPartData(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {

        }
    }

    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View_Controller/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController ModifyProductController = loader.getController();
            ModifyProductController.setProductData(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {

        }

    }

    @FXML
    void OnActionSearchParts(ActionEvent event) {
        String searchTxt = partSearchField.getText();

        ObservableList<Part> parts = Inventory.lookupPart(searchTxt);
        partTableView.setItems(parts);
        partSearchField.setText("");

        if(parts.size() == 0){
            try {
                int searchFieldPartId = Integer.parseInt(searchTxt);
                Part part = Inventory.lookupPart(searchFieldPartId);
                if (part != null) {
                    parts.add(part);
                }
            }
            catch (NumberFormatException e){
                //Prevent NumberFormatException error
            }
        }

    }

    @FXML
    void OnActionSearchProduct(ActionEvent event) {
        String searchTxt = productSearchField.getText();

        ObservableList<Product> products = Inventory.lookupProduct(searchTxt);
        productTableView.setItems(products);
        productSearchField.setText("");

        if(products.size() == 0){
            try {
                int searchFieldProductId = Integer.parseInt(searchTxt);
                Product product = Inventory.lookupProduct(searchFieldProductId);
                if (products != null) {
                    products.add(product);
                }
            }
            catch (NumberFormatException e){
                //Prevent NumberFormatException error
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
