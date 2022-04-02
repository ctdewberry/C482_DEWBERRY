package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.scene.control.*;

import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The type Modify part controller.
 */
public class ModifyPartController implements Initializable{

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;
    /**
     * The Error messages.
     * Array list for gathering error messages when user tries to modify a part
     */

    ArrayList<String> errorMessages = new ArrayList<String>();
    @FXML
    private RadioButton inHouseID;
    @FXML
    private RadioButton outsourcedID;
    @FXML
    private TextField partIDTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partMinTxt;
    @FXML
    private TextField partOtherTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private ToggleGroup inHouseOut;
    @FXML
    private Text modPartSourceToggle;
    /**
     * This attribute used to determine status of inhouse/outsourced radio buttons
     */
    private boolean toggleSource = false;

    /**
     * On action cancel.
     * Confirms user wishes to cancel modifying part
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
             *  brings user back to main menu
             *  */
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     *Method that adds various error messages to the errorMessages ArrayList to present
     *to the user when they try to modify a part
    */
    private void errorMessagesAdd(String errorMessage, String type) {
        /**
         * if the value entered is less than 0
         */

        if (type == "subZero") {
            errorMessages.add(errorMessage + " must not be less than 0");
        }
        /**
         * if the value entered does not match the expected type
         */

        if (type == "mismatch") {
            errorMessages.add("Please put a numerical value in the " + errorMessage + " field");
        }
        /**
         * if the inventory is set to a value that is less than what min is set to
         */

        if (type == "inventoryMin") {
            errorMessages.add(errorMessage + " must not be less than min.");
        }
        /**
         * if the inventory is set to a value that is higher than what max is set to
         */


        if (type == "inventoryMax") {
            errorMessages.add(errorMessage + " must not be greater than max");
        }
        /**
         * if min is greater than max
         */

        if (type == "minMax") {
            errorMessages.add(errorMessage + " cannot be greater than max");
        }
        /**
         * if a field is empty
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
    / Method that clears the error messages
    */
    private void clearErrorMessages() {
        errorMessages.clear();
    }

    /**
     * On action modify part.
     * Method used to modify part when customer clicks 'save' button
     * @param event the event
     * @throws IOException the io exception
     */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        /**
         * Confirms with user that they want to modify the part with the information they have entered
         */

        Alert alertConfirmPartModify = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmPartModify.setTitle("Modify Part");
        alertConfirmPartModify.setHeaderText("Modify");
        alertConfirmPartModify.setContentText("Do you want to Modify this part?");

        Optional<ButtonType> result = alertConfirmPartModify.showAndWait();
        if (result.get() == ButtonType.OK) {

            /**
             * This attribute gets the ID that was pre-populated in the ID field
             */

            int id = Integer.parseInt(partIDTxt.getText());

            /**
             * This variable is set to false. It will be used later when
             * determining what errors the user has made
             */

            boolean isAlpha = false;

            /**
             * This line gets the part Name entered by the user after confirming that
             * the name field is not empty. If it is empty, an error saying so will be added
             * to the errorMessages ArrayList created earlier
             */


            if (partNameTxt.getText().isEmpty()) {
                errorMessagesAdd("Name", "empty");
            }
            /**
             * If the Name field is not empty, its contents are transferred to a variable
             * that will be used when modifying the part
             */

            String name = partNameTxt.getText();
            /**
             * Stock is initially set to 0 for error reporting purposes
             */


            int stock = 0;
            /**
             * This try statement will catch if an empty or non numerical value was entered
             */

            try {
                stock = Integer.parseInt(partInvTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (partInvTxt.getText().isEmpty()) {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */
                    errorMessagesAdd("Inv", "empty");
                } else {
                    errorMessagesAdd("Inv", "mismatch");
                    /**
                     * Set variable isAlpha to true for error reporting purposes
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
                price = Double.parseDouble(partPriceTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (partPriceTxt.getText().isEmpty()) {
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
                min = Integer.parseInt(partMinTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (partMinTxt.getText().isEmpty()) {
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
                max = Integer.parseInt(partMaxTxt.getText());
            } catch (Exception entryError) {
                /**
                 * If the field was empty, add an error to errorMessages
                 */

                if (partMaxTxt.getText().isEmpty()) {
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
             * Determine which radio button the user has selected for part type (inHouse or outsourced)
             */

            if (toggleSource) {
                /**
                 * If set to outsourced, parse the name of the company that the user entered
                 */

                String companyName = partOtherTxt.getText();
                /**
                 * Add error to errorMessages if field was left blank
                 */

                if (companyName.isEmpty()) {
                    errorMessagesAdd("Company Name", "empty");
                }
            } else {
                /**
                 * If set to inHouse, parse the machineID that the user entered
                 */
                try {
                    Integer.parseInt(partOtherTxt.getText());
                } catch (Exception f) {
                    if (partOtherTxt.getText().isEmpty()) {
                        /**
                         * Add error to errorMessages if field was left blank
                         */

                        errorMessagesAdd("Machine ID", "empty");
                    } else {
                        /**
                         * Add error to errorMessages if field was entered with non-numeric characters
                         */
                        errorMessagesAdd("Machine ID", "mismatch");
                    }
                }
            }
            /**
             * This part finds out the allParts array index of the product currently being
             * modified. When the finalization of the modification occurs, this indexID will
             * be used to create a new part to replace the old part at the corresponding index
             *
             * The correct array location is confirmed by comparing the id of the part
             * currently being modified, with all of the IDs in the allParts array and
             * then obtaining the index at that location
             *
             * initialize variable used to determine the array index
             */


            int indexID = 0;
            /**
             * compare the current ID with that of all the items in the allParts array
             */


            for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                if (Inventory.getAllParts().get(i).getId() == id) {
                    /**
                     * save the array index for later use
                     */


                    indexID = Inventory.getAllParts().indexOf(Inventory.getAllParts().get(i));
                }
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
                /**
                 * Proceed with modifying the part if there were no errors
                 */

                /**
                 * If the radio button was set to inHouse, ensure modified part is of
                 * subclass inHouse and parse the machineID entered from the appropriate field
                 */


            } else if (inHouseID.isSelected()) {
                int machineid = Integer.parseInt(partOtherTxt.getText());
                /**
                 * Create a new part that will replace the part being modified
                 */

                Part part = new InHouse(id, name, price, stock, min, max, machineid);
                /**
                 * Replace old part with modified part at the specified index
                 */


                Inventory.updatePart(indexID, part);
                /**
                 * Send user back to the main screen after part modification
                 */


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } else {
                /**
                 * If the radio button was set to outsourced, ensure modified part is of
                 * subclass outsourced and parse the company name entered from the appropriate field
                 */


                String companyName = String.valueOf(partOtherTxt.getText());
                Part part = new Outsourced(id, name, price, stock, min, max, companyName);
                /**
                 * replace old part with modified at the specified index
                 */


                Inventory.updatePart(indexID, part);
                /**
                 * Send user back to the main screen after part creation
                 */


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
    }

    /**
     * Send part.
     * Method that receives the information about the selected part
     * from the main menu and then pre-populates the fields
     * @param part the part
     */

    public void sendPart(Part part)
    {
        partIDTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText((part.getName()));

        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        /**
         * This section determines the part subclass and sets the inhouse/outsourced
         * radio button and form text accordingly
         */


        if (part instanceof InHouse) {
            partOtherTxt.setText((String.valueOf(((InHouse) part).getMachineid())));
            inHouseID.setSelected(true);
            modPartSourceToggle.setText("Machine ID");
        } else {
            partOtherTxt.setText(((Outsourced) part).getCompanyName());
            outsourcedID.setSelected(true);
            modPartSourceToggle.setText("Company Name");
        }
    }

    /**
     * On click in house.
     * Method to change the text on the form to 'Machine ID' when the appropriate radio button is selected
     * @param action the action
     */

    public void onClickInHouse(ActionEvent action)
    {
        modPartSourceToggle.setText("Machine ID");
        toggleSource = false;
    }

    /**
     * On click outsourced.
     * Method to change the text on the form to 'Company Name' when the appropriate radio button is selected
     * @param action the action
     */

    public void onClickOutsourced(ActionEvent action)
    {
        modPartSourceToggle.setText("Company Name");
        toggleSource = true;
    }

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
