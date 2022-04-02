package Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * The type Main menu controller.
 */
public class MainMenuController implements Initializable{

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private TextField productText;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer > partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productCostCol;

    @FXML
    private TextField partText;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    /**
     * On action exit.
     * method that confirms user wishes to exit the program when pressing the 'Exit' button
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exit program");
        alert.setContentText("Do you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**
     * Enter product text.
     * method that allows for searching the allProducts OAL
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void enterProductText(ActionEvent event) throws Exception{
        productTableView.setItems(Inventory.getAllProducts());
        Inventory.clearFilteredProducts();

        /**
         * Method first tries to do a search by the number entered by the user
         * It does this by first trying to parse an integer from the text entered
         * If an item is found, it is put into the filteredParts OAL
         */

        try {
            Integer.parseInt(productText.getText());
            int testInt = Integer.parseInt(productText.getText());
            Inventory.addFilteredProduct(Inventory.lookupProduct(testInt));
            productTableView.setItems(Inventory.getFilteredProducts());

            /**
             *if nothing has been found then filteredProducts is null,
             * we can let the user know nothing has been found with an alert
             */
            if (Inventory.getFilteredProducts().get(0) == null) {
                Alert noItemsNumerical = new Alert(Alert.AlertType.ERROR);
                noItemsNumerical.setTitle("No items found");
                noItemsNumerical.setHeaderText("No items found");
                noItemsNumerical.setContentText("No items found");
                noItemsNumerical.showAndWait();
            }

            /**
             * the 'Try' block will fail if we attempt to parse an integer from text.
             * We can use this exception to our advantage to determine that the user
             * is instead entering letters
             */

        } catch (NumberFormatException e) {
            /**
             * catch block, if nothing has been entered, we can display the entire list
             */
            if (productText.getText().equals("")) {
                productTableView.setItems(Inventory.getAllProducts());
            } else {
                try {
                    /**
                     * if, instead something has been entered, we can proceed with our search
                     * Inventory.lookupProduct takes the string entered by the user and will return full
                     * and partial matches. Any matches are added to the filteredProducts OAL
                     */

                    Inventory.lookupProduct(productText.getText());
                    productTableView.setItems(Inventory.getFilteredProducts());
                    /**
                     * If no items are found, we let the user know
                    */
                    if (Inventory.getFilteredProducts().isEmpty()) {
                        Alert noItemsNumerical = new Alert(Alert.AlertType.ERROR);
                        noItemsNumerical.setTitle("No items found");
                        noItemsNumerical.setHeaderText("No items found");
                        noItemsNumerical.setContentText("No items found");
                        noItemsNumerical.showAndWait();
                    }
                    /**
                     * Any  errors due to NumberFormat will result in the entire list being shown for the user
                     */

                } catch (NumberFormatException d) {
                    productTableView.setItems(Inventory.getAllProducts());
                }
            }
        }
        /**
         * any errors due to NullPointers will result in another search on the string being done
         */
        catch (NullPointerException e) {
            productTableView.setItems(Inventory.lookupProduct((productText.getText())));
        }
    }

    /**
     * Enter part text.
     * method that allows for searching the allParts OAL
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void enterPartText(ActionEvent event) throws IOException {
        partsTableView.setItems(Inventory.getAllParts());
        Inventory.clearFilteredParts();

        /**
         * Method first tries to do a search by the number entered by the user
         * It does this by first trying to parse an integer from the text entered
         * If an item is found, it is put into the filteredParts OAL
         */

        try {
            Integer.parseInt(partText.getText());
            int testInt = Integer.parseInt(partText.getText());
            Inventory.addFilteredPart(Inventory.lookupPart(testInt));
            partsTableView.setItems(Inventory.getFilteredParts());

            /**
             * if nothing has been found, then filteredParts is null,
             * we can let the user know nothing has been found with an alert
             */

            if (Inventory.getFilteredParts().get(0) == null) {
                Alert noItemsNumerical = new Alert(Alert.AlertType.ERROR);
                noItemsNumerical.setTitle("No items found");
                noItemsNumerical.setHeaderText("No items found");
                noItemsNumerical.setContentText("No items found");
                noItemsNumerical.showAndWait();
            }
            /**
             * the 'Try' block will fail if we attempt to parse an integer from text.
             * We can use this exception to our advantage to determine that the user
             * is instead entering letters
             */


        } catch (NumberFormatException e) {
            /**
             * Catch block: if nothing has been entered, we can display the entire list
             */

            if (partText.getText().equals("")) {
                partsTableView.setItems(Inventory.getAllParts());
            } else {
                try {

                    /** if, instead something has been entered, we can proceed with our search
                    *Inventory.lookupPart takes the string entered by the user and will return full
                    * and partial matches. Any matches are added to the filteredParts OAL
                    */
                    Inventory.lookupPart(partText.getText());
                    partsTableView.setItems(Inventory.getFilteredParts());

                    /**
                     * If no items are found, we let the user know
                     */

                    if (Inventory.getFilteredParts().isEmpty()) {
                        Alert noItemsWord = new Alert(Alert.AlertType.ERROR);
                        noItemsWord.setTitle("No items found");
                        noItemsWord.setHeaderText("No items found");
                        noItemsWord.setContentText("No items found");
                        noItemsWord.showAndWait();
                    }
                    /**
                     * Any  errors due to NumberFormat will result in the entire list being shown for the user
                     */
                } catch (NumberFormatException d) {
                    partsTableView.setItems(Inventory.getAllParts());
                }
            }
        }
        /**
         * any errors due to NullPointers will result in another search on the string being done
         */

        catch (NullPointerException e) {
            partsTableView.setItems(Inventory.lookupPart((partText.getText())));
        }
    }

    /**
     * On action add part.
     * method that opens up the addPart screen, and send the information about the selected part to the new view
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException  {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddPartMenu.fxml"));
        loader.load();

        AddPartController AddPartController = loader.getController();
        AddPartController.sendID();

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action add product.
     * method that opens up the addProduct screen, and send the information about the selected product to the new view
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddProductMenu.fxml"));
        loader.load();

        AddProductController AddProductController = loader.getController();
        AddProductController.sendID();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * On action delete part.
     * method that deletes a part from allParts and updates the table view
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionDeletePart(ActionEvent event) throws IOException {
        /**
         * gives error if nothing is selected
         */

        if (partsTableView.getSelectionModel().getSelectedItem() == null) {
            Alert noItemsSelected = new Alert(Alert.AlertType.CONFIRMATION);
            noItemsSelected.setTitle("Unable to delete");
            noItemsSelected.setHeaderText("Unable to delete");
            noItemsSelected.setContentText("There are no selected items to delete.");
            noItemsSelected.showAndWait();
            /**
             * if a part is found, the system confirms with user that the part is to be deleted
             */

        } else {
            Alert alertConfirmPartDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmPartDelete.setTitle("Delete Part");
            alertConfirmPartDelete.setHeaderText("Delete");
            alertConfirmPartDelete.setContentText("Do you want to delete part: " + partsTableView.getSelectionModel().getSelectedItem().getName() );

            Optional<ButtonType> result = alertConfirmPartDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                /**
                 * part is deleted and the table view is refreshed with the updated list of parts
                 */

                Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
                partsTableView.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * On action delete product.
     * method that deletes a product from allProducts and updates the table view
     * @param event the event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        /**
         * gives error if nothing is selected
         */

        if (productTableView.getSelectionModel().getSelectedItem() == null) {
            Alert noItemsSelected = new Alert(Alert.AlertType.CONFIRMATION);
            noItemsSelected.setTitle("Unable to delete");
            noItemsSelected.setHeaderText("Unable to delete");
            noItemsSelected.setContentText("There are no selected items to delete.");
            noItemsSelected.showAndWait();
            /**
             * if a product is found, the system confirms with user that the product is to be deleted
             */

        } else {
            Alert alertConfirmProductDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmProductDelete.setTitle("Delete Product");
            alertConfirmProductDelete.setHeaderText("Delete");
            alertConfirmProductDelete.setContentText("Do you want to delete this product?");

            Optional<ButtonType> result = alertConfirmProductDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                /**
                 * system checks to see if there are any parts associated to the product
                 * If there are associated parts, the system advised the user to delete the associated parts before
                 * trying to delete the selected product
                 */

                if (!productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {

                    Alert stillHasParts = new Alert(Alert.AlertType.CONFIRMATION);
                    stillHasParts.setTitle("Unable to delete");
                    stillHasParts.setHeaderText("Unable to delete");
                    stillHasParts.setContentText("This product has one or more parts associated with it. Unable to delete selected product. Please delete all associated parts before trying again");
                    stillHasParts.showAndWait();
                    /**
                     * Product is deleted and the table view is refreshed with the updated list of products
                     */
                } else {
                    Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
                    productTableView.setItems(Inventory.getAllProducts());
                }
            }
        }
    }

    /**
     * On action modify part.
     * method that sends selected part to the modify part screen
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException  {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));
            loader.load();

            ModifyPartController ModPartController = loader.getController();
            ModPartController.sendPart(partsTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            /**
             * Catches NullPointerException if user clicks 'modify' without having selected a part
             */

        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * On action modify product.
     * method that sends selected product to the modify product screen
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException  {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
            loader.load();

            ModifyProductController ModProductController = loader.getController();
            ModProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            /**
             *  //Catches NullPointerException if user clicks 'modify' without having selected a part
             */

        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * sets partsTableView with allParts OAL
         */

        partsTableView.setItems(Inventory.getAllParts());

        /**
         * sets partsTableView columns
         */

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * sets productTableView with allProducts OAL
         */

        productTableView.setItems(Inventory.getAllProducts());

        /**
         * sets productTableView columns
         */

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
