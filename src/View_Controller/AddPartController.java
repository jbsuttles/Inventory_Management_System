package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private ToggleGroup partTG;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMachIdTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private Label inout;

    @FXML
    private TextField companyNameTxt;


    // Cancel button function
    @FXML
    void OnActionCancelAdd(ActionEvent event) throws IOException {

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

    // Save button function
    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException {


        try {
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());

            if ((stock > max )|| (stock < min) || (max < min)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Inv field must be between Max and Min.\n Max cannot be less than Min.");
                alert.showAndWait();
            } else {

                // InHouse Part
                int id = Inventory.addInventoryPartId();
                if (inHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(partMachIdTxt.getText());
                    Inventory.addPart(new InhousePart(id, name, price, stock, min, max, machineId));
                }

                // Outsourced Part
                if (outsourcedRBtn.isSelected()) {
                    String companyName = companyNameTxt.getText();
                    Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, companyName));
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }

        // Warning if Inventory, Price, Min, or Max field has been left blank
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a number in the Inventory, Price, Min and Max fields.");
            alert.showAndWait();
        }

    }

    // Radio button function for InHouse or Outsoucred Part
    public void radioCheck(ActionEvent event){
        String inHouse = "Machine ID";
        String outsource = "Company Name";
        String message = "";

       if(inHouseRBtn.isSelected()){
           message += inHouse;
        }

        if(outsourcedRBtn.isSelected()){
           message += outsource;
        }

        inout.setText(message);

        if(inHouseRBtn.isSelected()){
            partMachIdTxt.setVisible(true);
            companyNameTxt.setVisible(false);
        }

        if(outsourcedRBtn.isSelected()){
            companyNameTxt.setVisible(true);
            partMachIdTxt.setVisible(false);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inout.setText("Machine ID");
        partMachIdTxt.setVisible(true);
        companyNameTxt.setVisible(false);

    }
}
