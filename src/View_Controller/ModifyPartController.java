package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;
    Part part;
    Part modifiedPart;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private ToggleGroup partTG;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Label inout;

    @FXML
    private TextField partId;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partMachIdTxt;

    @FXML
    private TextField companyNameTxt;

    // Cancel button function
    @FXML
    void OnActionCancelModify(ActionEvent event) throws IOException {

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

    public static int indexOfPart(Part part){
        ObservableList allparts = Inventory.getAllParts();
        int index = allparts.indexOf(part);
        return index;
    }

    public void setPartData(Part part) {
       this.part = part;

        partId.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));

        //If selected part is an Inhouse part
        InhousePart inhousePart;

        if(part instanceof InhousePart){
            inhousePart = (InhousePart)part;
            inout.setText("Machine ID");
            partMachIdTxt.setVisible(true);
            companyNameTxt.setVisible(false);
            inHouseRBtn.setSelected(true);
            partMachIdTxt.setText(String.valueOf(inhousePart.getMachineId()));
        }

        //If selected part is an Outsourced part
        OutsourcedPart outsourcedPart;

        if(part instanceof OutsourcedPart){
            outsourcedPart = (OutsourcedPart)part;
            inout.setText("Company Name");
            companyNameTxt.setVisible(true);
            partMachIdTxt.setVisible(false);
            outsourcedRBtn.setSelected(true);
            companyNameTxt.setText(outsourcedPart.getCompanyName());
        }

    }

    // Save button function
    @FXML
    void OnActionSavePartModify(ActionEvent event)  throws IOException {
        this.modifiedPart = part;

        try {
            int id = this.part.getId();
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Inv field must be between Max and Min field");
                alert.showAndWait();
            } else if (max < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Max field cannot be less than Min field.");
                alert.showAndWait();
            } else {

                // InHouse Part
                if (inHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(partMachIdTxt.getText());
                    InhousePart updatedInHousePart = new InhousePart(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(indexOfPart(this.part), updatedInHousePart);
                }

                // Outsourced Part
                if (outsourcedRBtn.isSelected()) {
                    String companyName = companyNameTxt.getText();
                    OutsourcedPart updatedOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(indexOfPart(this.part), updatedOutsourcedPart);
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

    }
}
