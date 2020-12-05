package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
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

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    Product Product;
    Part part;
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    @FXML
    private TextField productId;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

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
    private TableView<Part> addedTableView;

    @FXML
    private TableColumn<Part, Integer> addedPartIdCol;

    @FXML
    private TableColumn<Part, String> addedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addedPartInvCol;

    @FXML
    private TableColumn<Part, Double> addedPartPriceCol;

    // Add button function
    @FXML
    void OnActionAddPart(ActionEvent event) {
        part = (partTableView.getSelectionModel().getSelectedItem());
        selectedParts.add(part);

        addedTableView.setItems(selectedParts);

        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    // Cancel button function
    @FXML
    void OnActionCancelProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to continue? Unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    // Delete button function
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the part from the product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            part = addedTableView.getSelectionModel().getSelectedItem();
            selectedParts.remove(part);
            addedTableView.getItems().remove(part);
        }
    }

    // Save button function
    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException {

        try {
            int id = this.Product.getId();
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());


            if ((stock > max) || (stock < min) || (max < min)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Inv field must be between Max and Min.\n Max cannot be less than Min.");
                alert.showAndWait();
            } else if (selectedParts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Warning");
                alert.setContentText("At least one part must be associated with product.");
                alert.showAndWait();
            } else {

                Product updatedProduct = new Product(id, name, price, stock, min, max);
                Inventory.updateProduct(indexOfProduct(this.Product), updatedProduct);
                updatedProduct.addAssociatedPart(selectedParts);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a number in the Inventory, Price, Min and Max fields.");
            alert.showAndWait();
        }

    }

    public static int indexOfProduct(Product product){
        ObservableList allproducts = Inventory.getAllProducts();
        int index = allproducts.indexOf(product);
        return index;
    }


    // Search button function
    @FXML
    void OnActionSearchParts(ActionEvent event) {
        String searchTxt = partSearchField.getText();

        ObservableList<Part> parts = Inventory.lookupPart(searchTxt);
        partTableView.setItems(parts);
        partSearchField.setText("");

        if (parts.size() == 0) {
            try {
                int searchFieldId = Integer.parseInt(searchTxt);
                Part part = Inventory.lookupPart(searchFieldId);
                if (part != null) {
                    parts.add(part);
                }
            }catch(NumberFormatException e){
                //Prevent NumberFormatException error
            }
        }

    }

    // Set product data
    public void setProductData(Product product) {
        this.Product = product;

        productId.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(String.valueOf(product.getStock()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productMaxTxt.setText(String.valueOf(product.getMax()));
        productMinTxt.setText(String.valueOf(product.getMin()));


        addedTableView.setItems(product.getAllAssociatedParts());

        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        selectedParts = product.getAllAssociatedParts();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
