package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int inventoryPartId;
    private static int inventoryProductId;

    public static void addPart(Part part){
        allParts.add(part);
    }

    public static void addProduct(Product product){
        allProducts.add(product);
    }


    public static int addInventoryPartId(){
        inventoryPartId++;
        return inventoryPartId;
    }

    public static int addInventoryProductId(){
        inventoryProductId++;
        return  inventoryProductId;
    }

    // Search for part by ID
    public static Part lookupPart(int partId){
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part searchedPartId : allParts){
            if(searchedPartId.getId() == partId){
                return searchedPartId;
            }
        }
        return null;
    }

    // Search for part by Name
    public static ObservableList <Part> lookupPart (String partName) {

        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part searchedPartName : allParts){
            if(searchedPartName.getName().contains(partName)){
                searchedParts.add(searchedPartName);
            }
        }
        return searchedParts;

    }

    // Search for product by ID
    public static Product lookupProduct(int productId){
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product searchedProductId : allProducts){
            if(searchedProductId.getId() == productId){
                return searchedProductId;
            }
        }
        return null;

    }

    // Search for product by Name
    public static ObservableList <Product> lookupProduct (String productName){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product searchedProductName : allProducts){
            if(searchedProductName.getName().contains(productName)){
                searchedProducts.add(searchedProductName);
            }
        }
        return searchedProducts;

    }

    public static void updatePart(int index, Part updatedPart){
        allParts.set(index, updatedPart);
    }

    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart){
        return getAllParts().remove(selectedPart);
    }


    public static boolean deleteProduct(Product selectedProduct){
        return getAllProducts().remove(selectedProduct);
    }

    public static ObservableList <Part> getAllParts(){
        return allParts;
    }

    public static ObservableList <Product> getAllProducts(){
        return allProducts;
    }


}
