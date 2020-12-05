package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

//Stat the main menu when the program is run
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1377, 525));
        primaryStage.show();
    }

// Add initial parts and products
    public static void main(String[] args) {

        InhousePart part = new InhousePart(1, "Wheel", 28.5, 5, 2, 6, 253);
        OutsourcedPart part2 = new OutsourcedPart(2, "out", 6, 28, 20, 30, "shop");
        InhousePart part3 = new InhousePart(3, "handle", 28.5, 5, 2, 6, 253);
        Product product = new Product(25, "brakes", 35.5, 2, 1, 3);
        Product product2 = new Product(12, "frames", 60, 2, 1, 3);

        Inventory.addPart(part);
        Inventory.addProduct(product);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product2);

        launch(args);
    }
}
