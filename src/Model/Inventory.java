package Model;

import Controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

/**
 * The type Inventory.
 */
public class Inventory {
    /**
     * create an observable array list for all parts in inventory
     */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * create a temporary observable array list for all parts that match the search criteria entered by the user
     * this list gets reset after every search
     */

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    /**
     * create an observable array list for all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * create a temporary observable array list for all products that match the search criteria entered by the user
     * this list gets reset after every search
     */

    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();



    /**
     * addPart
     * method to add a new part to the allParts observableArrayList
     * @param newPart the new part
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * AddProduct.
     *method to add a new product to the allProducts observableArrayList
     * @param product the product
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * LookupPart part.
     * method to lookup a part based on user criteria (numerical)
     * first, the OAL filteredParts is reset, then the method compares the user's numerical search query and compares
     * to the list of allParts, returning a part if it has a matching ID
     * @param partId the part id
     * @return the part
     */

    public static Part lookupPart(int partId) {
        filteredParts.clear();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                return allParts.get(i);
            }
        }
        return null;
    }

    /**
     * LookupPart observable list.
     * method to lookup parts based on user criteria (String)
     * first, the OAL filteredParts is reset, then the method compares the user's String search query and compares
     * to the list of allParts, returning an observable list containing all the items that partially or fully match
     * @param partName the part name
     * @return the observable list
     */

    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();

        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().contains(partName)) {
                filteredParts.add(allParts.get(i));
            }
        }
        return filteredParts;
    }

    /**
     * LookupProduct product.
     * method to lookup a product based on user criteria (numerical)
     * first, the OAL filteredProducts is reset, then the method compares the user's numerical search query and compares
     * to the list of allProducts, returning a product if it has a matching ID
     * @param productID the product id
     * @return the product
     */

    public static Product lookupProduct(int productID) {
        filteredProducts.clear();

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productID) {
                return allProducts.get(i);
            }
        }
        return null;
    }

    /**
     * LookupProduct observable list.
     * method to lookup products based on user criteria (String)
     * first, the OAL filteredProducts is reset, then the method compares the user's String search query and compares
     * to the list of allProducts, returning an observable list containing all the items that partially or fully match
     * @param productName the product name
     * @return the observable list
     */

    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().contains(productName)) {
                filteredProducts.add(allProducts.get(i));
            }
        }
        return filteredProducts;
    }

    /**
     * AddFilteredPart.
     * method to add a part to the temporary OAL called filteredParts
     * @param newPart the new part
     */
    public static void addFilteredPart(Part newPart) {
        filteredParts.add(newPart);
    }

    /**
     * Add filtered product.
     * method to add a part to the temporary OAL called filteredProducts
     * @param newProduct the new product
     */
    public static void addFilteredProduct(Product newProduct) {
        filteredProducts.add(newProduct);
    }

    /**
     * UpdatePart.
     *m ethod to modify a part by updating the part and replacing it old part at a given index of allParts
     * @param index        the index
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * UpdateProduct.
     * method to modify a product by updating the product and replacing the old product at a given index of allProducts
     * @param index           the index
     * @param selectedProduct the selected product
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * DeletePart boolean.
     * method to remove a part from allParts OAL
     * @param selectedPart the selected part
     * @return the boolean
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * DeleteProduct boolean.
     * method to remove a part from allProducts OAL
     * @param selectedProduct the selected product
     * @return the boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts.
     * method to return the observableArrayList entitled allParts
     * @return the all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products.
     * method to return the observableArrayList entitled allProducts
     * @return the all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Gets filtered parts.
     * method to return the temporary observableArrayList entitled filteredParts
     * @return the filtered parts
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * Clear filtered parts.
     * method to reset the temporary observableArrayList entitled filteredParts
     */
    public static void clearFilteredParts() {
        filteredParts.clear();
    }

    /**
     * Gets filtered products.
     * method to return the temporary observableArrayList entitled filteredProducts
     * @return the filtered products
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * Clear filtered products.
     * method to reset the temporary observableArrayList entitled filteredProducts
     */
    public static void clearFilteredProducts() {
        filteredProducts.clear();
    }

}
