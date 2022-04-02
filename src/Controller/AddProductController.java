package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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


/**
 * The type Add product controller.
 */
public class AddProductController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * ObservableList tempAddedParts
     * Temporary OAL used for when user is adding and removing associated parts from the
     * product they are creating. Once they finalize creating the product, the
     * contents from tempAddedParts are transferred to the permanent associatedParts OAL
     * for the newly created product
     */

    private static ObservableList <Part> tempAddedParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableView<Part> associatedPartsViewTable;

    @FXML
    private TableColumn<Part, Integer > partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Part, Integer > partIDColAP;

    @FXML
    private TableColumn<Part, String> partNameColAP;

    @FXML
    private TableColumn<Part, Integer> partInvColAP;

    @FXML
    private TableColumn<Part, Double> partPriceColAP;

    @FXML
    private TextField partText;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField prodPriceTxt;

    @FXML
    private TextField prodIDTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodInvTxt;

    /**
     * On action cancel.
     * Confirms user wishes to cancel adding product
     * @param event the event
     * @throws IOException the io exception
     */

    @FXML
    void onActionCancel(ActionEvent event)throws IOException {
        Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCancel.setTitle("Cancel");
        alertConfirmCancel.setHeaderText("Cancel");
        alertConfirmCancel.setContentText("Do you want to Cancel?");

        Optional<ButtonType> result = alertConfirmCancel.showAndWait();
        if (result.get() == ButtonType.OK){
            /**
             * brings user back to main menu
             */


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * The Error messages.
     * Array list for gathering error messages when user tries to add a product
     */

    ArrayList<String> errorMessages = new ArrayList<String>();
    /**
     * Method that adds various error messages to the errorMessages ArrayList
     * to present to the user when they try to create a product
     */


    private void errorMessagesAdd(String errorMessage, String type) {
        /**
         * Adds error to error messages array if the value entered is less than 0
         */

        if (type == "subZero") {
            errorMessages.add(errorMessage + " must not be less than 0");
        }
        /**
         * Adds error to error messages array if the value entered does not match the expected type
         */

        if (type == "mismatch") {
            errorMessages.add("Please put a numerical value in the " + errorMessage + " field");
        }
        /**
         * Adds error to error messages array if the inventory is set to a value that is less than what min is set to
         */


        if (type == "inventoryMin") {
            errorMessages.add(errorMessage + " must not be less than min.");
        }
        /**
         * Adds error to error messages array if the inventory is set to a value that is higher than what max is set to
         */


        if (type == "inventoryMax") {
            errorMessages.add(errorMessage + " must not be greater than max");
        }
        /**
         * Adds error to error messages array if min is greater than max
         */


        if (type == "minMax") {
            errorMessages.add(errorMessage + " cannot be greater than max");
        }
        /**
         * Adds error to error messages array if a field is empty
         */


        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }
    }
    /**
     * Method that returns the accumulated error messages
     */


    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }
    /**
     * Method that clears the error messages
     */


    private void clearErrorMessages() {
        errorMessages.clear();
    }

    /**
     * On action create product.
     * Method used to create product when customer clicks 'save' button
     * @param event the event
     * @throws IOException the io exception
     */

    @FXML
    void onActionCreateProduct(ActionEvent event) throws IOException{
        /**
         * Confirms with user that they want to create a product with the information they have entered
         */


        Alert alertConfirmProductCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmProductCreation.setTitle("Create Product");
        alertConfirmProductCreation.setHeaderText("Create");
        alertConfirmProductCreation.setContentText("Do you want to create this product?");

        Optional<ButtonType> result = alertConfirmProductCreation.showAndWait();
        if (result.get() == ButtonType.OK) {
            /**
             * This attribute gets the ID that was pre-populated in the ID field
             */


            int id = Integer.parseInt(prodIDTxt.getText());
            /**
             * This variable is set to false. It will be used later when
             * determining what errors the user has made
             */


            boolean isAlpha = false;
            /**
             * This line gets the product Name entered by the user after confirming that
             * the name field is not empty. If it is empty, an error saying so will be added
             * to the errorMessages ArrayList created earlier
             */


            if (prodNameTxt.getText().isEmpty()) {
                errorMessagesAdd("Name", "empty");
            }
            /**
             * If the Name field is not empty, its contents are transferred to a variable
             * that will be used when creating the product
             */

            String name = prodNameTxt.getText();
            /**
             * Stock is initially set to 0 for error reporting purposes
             *
             */


            int stock = 0;
            /**
             * This try statement will catch if an empty or non numerical value was entered
             */

            try {
                stock = Integer.parseInt(prodInvTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (prodInvTxt.getText().isEmpty()) {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */

                    errorMessagesAdd("Inv", "empty");
                } else {
                    errorMessagesAdd("Inv", "mismatch");
                    /**
                     * Set variable to true for error reporting purposes
                     */

                    isAlpha = true;
                }
            }
            /**
             * Add error to errorMessages if the inventory entered is less than 0
             */

            if (stock < 0) {
                errorMessagesAdd("Stock", "subZero");
            }

            /**
             * Price is initially set to 0 for error reporting purposes
             */

            Double price = 0.0;
            /**
             * This try statement will catch if an empty or non numerical value was entered for price
             */

            try {
                price = Double.parseDouble(prodPriceTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (prodPriceTxt.getText().isEmpty()) {
                    errorMessagesAdd("Price", "empty");
                } else {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */

                    errorMessagesAdd("Price", "mismatch");
                }
            }
            /**
             * Add error to errorMessages if the price entered is less than 0
             */

            if (price < 0) {
                errorMessagesAdd("Price", "subZero");
            }

            /**
             * Min is initially set to 0 for error reporting purposes
             */

            int min = 0;
            /**
             * This try statement will catch if an empty or non numerical value was entered for min
             */
            try {
                min = Integer.parseInt(prodMinTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (prodMinTxt.getText().isEmpty()) {
                    errorMessagesAdd("Min", "empty");
                } else {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */

                    errorMessagesAdd("Min", "mismatch");
                }
            }
            if (min < 0) {
                /**
                 * Add error to errorMessages if the min entered is less than 0
                 */

                errorMessagesAdd("Min", "subZero");
            }

            /**
             * Max is initially set to 0 for error reporting purposes
             */

            int max = 0;
            /**
             * This try statement will catch if an empty or non numerical value was entered for max
             */
            try {
                max = Integer.parseInt(prodMaxTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (prodMaxTxt.getText().isEmpty()) {
                    errorMessagesAdd("Max", "empty");
                } else {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */
                    errorMessagesAdd("Max", "mismatch");
                }
            }
            if (max < 0) {
                /**
                 * Add error to errorMessages if the max entered is less than 0
                 */

                errorMessagesAdd("Max", "subZero");
            }
            /**
             * Add error to errorMessages if min > stock and inv was entered as a number
             */


            if (min > stock && isAlpha == false) {
                errorMessagesAdd("Inventory", "inventoryMin");
            }
            /**
             * Add error to errorMessages if stock > max and inv was entered as a number
             */

            if ((stock > max) && isAlpha == false) {
                errorMessagesAdd("Inventory", "inventoryMax");
            }
            /**
             * Add error to errorMessages if min > max
             */

            if (min > max) {
                errorMessagesAdd("Min", "minMax");
            }
            /**
             * Create an alert with all of the errors the user has accumulated
             */


            if (!(getErrorMessagesTotal().size() == 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Error");
                alert.setContentText(String.join("\n", getErrorMessagesTotal()));
                alert.showAndWait();
                clearErrorMessages();
            }  else {
                /**
                 * Proceed with creating a product if there were no errors
                 */

                Inventory.addProduct(new Product(id, name, price, stock, min, max));
                /**
                 * Finalize the associatedParts by transferring them from the tempAddedParts OAL
                 * to the associatedParts OAL for the product being created
                 */


                for (int i = 0; i < tempAddedParts.size(); i++) {
                    Inventory.getAllProducts().get(getArrayValue()).addAssociatedPart(tempAddedParts.get(i));
                }
                /**
                 * Send user back to the main screen after product creation
                 *
                 */


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
    }

    /**
     * Send id.
     * Method to get the highest productID that exists in allProducts array
     * and pre-populates the id field with an ID that is one higher
     */

    public void sendID() {
        int prodCount = Inventory.getAllProducts().get(Inventory.getAllProducts().size()-1).getId();
        prodIDTxt.setText((String.valueOf(prodCount+1)));
    }

    /**
     * On action add associated part.
     * method to add the selected part to the tempAddedParts OAL
     * @param event the event
     */

    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        /**
         * select the associated part to be added
         */


        tempAddedParts.add(partsTableView.getSelectionModel().getSelectedItem());
        /**
         * update table view with updated list of tempAddedParts
         */

        associatedPartsViewTable.setItems(tempAddedParts);
    }

    /**
     * On action remove associated part.
     * method to remove the selected part from the tempAddedParts OAL
     * @param event the event
     */

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        /**
         * User confirms removal of associated part from the tempAddedParts OAL
         */

        Alert alertConfirmPartDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmPartDelete.setTitle("Delete Part");
        alertConfirmPartDelete.setHeaderText("Delete");
        alertConfirmPartDelete.setContentText("Do you want to remove this part from this product?");

        Optional<ButtonType> result = alertConfirmPartDelete.showAndWait();
        if (result.get() == ButtonType.OK) {
            /**
             * select the associated part to be removed
             */

            tempAddedParts.remove(associatedPartsViewTable.getSelectionModel().getSelectedItem());
            /**
             * update table view with updated list of tempAddedParts
             */


            associatedPartsViewTable.setItems(tempAddedParts);
        }
    }
    /**
     * This method uses the auto generated ID to determine index of
     * allProducts where the final product will be stored.
     * The returned index is used to help move associated parts from
     * tempAddedParts to the finalized associatedParts for this product.
     */


    private int getArrayValue(){
        int id = Integer.parseInt(prodIDTxt.getText());

        int indexID = 0;

        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == id) {
                indexID = i;
            }
        }
        return indexID;
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
         * method first tries to do a search by the number entered by the user
         * it does this by first trying to parse an integer from the text entered
         * If an item is found, it is put into the filteredParts OAL
         */

        try {
            Integer.parseInt(partText.getText());
            int testInt = Integer.parseInt(partText.getText());
            Inventory.addFilteredPart(Inventory.lookupPart(testInt));
            partsTableView.setItems(Inventory.getFilteredParts());
            /**
             * if nothing has been found then filteredParts is null,
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
             *  the 'Try' block will fail if we attempt to parse an integer from text.
             *  We can use this exception to our advantage to determine that the user
             *  is instead entering letters
             */


        } catch (NumberFormatException e) {
            /**
             * if nothing has been entered, we can display the entire list
             */

            if (partText.getText().equals("")) {
                partsTableView.setItems(Inventory.getAllParts());
            } else {
                try {
                    /**
                     * if, instead something has been entered, we can proceed with our search
                     * Inventory.lookupPart takes the string entered by the user and will return full
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
         * sets associatedParts/tempAddedParts partsTableView columns
         */


        partIDColAP.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColAP.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColAP.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColAP.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
