package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The type Add part controller.
 */
public class AddPartController implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;
    /**
     * The Part id txt.
     */
    @FXML
    private TextField partIDTxt;

    /**
     * The Part name txt.
     */
    @FXML
    private TextField partNameTxt;

    /**
     * The Part inv txt.
     */
    @FXML
    private TextField partInvTxt;

    /**
     * The Part price txt.
     */
    @FXML
    private TextField partPriceTxt;

    /**
     * The Part min txt.
     */
    @FXML
    private TextField partMinTxt;

    /**
     * The Part other txt.
     */
    @FXML
    private TextField partOtherTxt;

    /**
     * The Part max txt.
     */
    @FXML
    private TextField partMaxTxt;


    /**
     * The Out sourced btn.
     */
    @FXML
    private RadioButton outSourcedBtn;

    /**
     * The In house out.
     */
    @FXML
    private ToggleGroup inHouseOut;

    /**
     * The In house btn.
     */
    @FXML
    private RadioButton inHouseBtn;

    /**
     * The Add part source toggle.
     */
    @FXML
    private Text addPartSourceToggle;

    /**
     * This attribute used to determine status of inhouse/outsourced radio buttons
     */
    private boolean toggleSource = false;

    /**
     * On action cancel.
     * Confirms user wishes to cancel adding part
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCancel.setTitle("Cancel");
        alertConfirmCancel.setHeaderText("Cancel");
        alertConfirmCancel.setContentText("Do you want to Cancel?");

        Optional<ButtonType> result = alertConfirmCancel.showAndWait();
        if (result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * <b>C482 Section G Part 1:</b>
     * <p>Provide a detailed description of a logical or runtime error that you corrected in the code
     * and a detailed description of how you corrected it above the line of code you are referring to
     * <p>
     * I, at first had some trouble getting the error messages to show up. Originally, they had multiple
     * popups for each error which is not a good user experience. My solution was to create an array list
     * of strings and various methods so that when the user would go to create or modify a part or product,
     * methods would be called that would check each input field to determine whether it was entered correctly
     * or not... eg a string when the field expected an integer, or a negative value. When a field did not pass
     * inspection it would call the method: errorMessagesAdd() and pass information to it that would provide
     * details about the error. This method would then add those details to the array list mentioned previously.
     * Finally, once all fields have been checked, a single alert dialog pops up that displays the array list
     * and provides details to the user about all of the fields that did not meet criteria and what specifically
     * was wrong with each of them. This allows for a clear and concise user experience. If I had more time,
     * I would definitely consolidate these methods, but for now it works and meets the criteria for the project.
     * The Error messages.
     * Array list for gathering error messages when user tries to add a part
     */
    ArrayList<String> errorMessages = new ArrayList<String>();

    /**
     * error Messages Add
     * Method that adds various error messages to the errorMessages ArrayList to present
     * to the user when they try to add a part
     *
     * @param errorMessage the error message
     * @param type         the type
     */
    private void errorMessagesAdd(String errorMessage, String type) {

        /**
         * adds to arrayList errorMessages if the value entered is less than 0
         */
        if (type == "subZero") {
            errorMessages.add(errorMessage + " must not be less than 0");
        }

        /**
         * adds to arrayList errorMessages if the value entered does not match the expected type
         */

        if (type == "mismatch") {
            errorMessages.add("Please put a numerical value in the " + errorMessage + " field");
        }

        /**
         * adds to arrayList errorMessages if the inventory is set to a value that is less than what min is set to
         */

        if (type == "inventoryMin") {
            errorMessages.add(errorMessage + " must not be less than min.");
        }

        /**
         * adds to arrayList errorMessages if the inventory is set to a value that is higher than what max is set to
         */

        if (type == "inventoryMax") {
            errorMessages.add(errorMessage + " must not be greater than max");
        }

        /**
         * adds to arrayList errorMessages if min is greater than max
         */

        if (type == "minMax") {
            errorMessages.add(errorMessage + " cannot be greater than max");
        }

        /**
         * adds to arrayList errorMessages if a field is empty
         */

        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }
    }

    /**
     * get Error Messages Total
     * Method that returns the accumulated error messages
     *
     * @return the error messages total
     */
    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }

    /**
     * clear Error Messages
     * Method that clears the error messages
     */
    private void clearErrorMessages() {
        errorMessages.clear();
    }

    /**
     * On action create part.
     * Method used to create part when customer clicks 'save' button
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionCreatePart(ActionEvent event) throws IOException {

        /**
         * Confirms with user that they want to create a part with the information they have entered
         */

        Alert alertConfirmPartCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmPartCreation.setTitle("Create Part");
        alertConfirmPartCreation.setHeaderText("Create");
        alertConfirmPartCreation.setContentText("Do you want to create this part?");

        Optional<ButtonType> result = alertConfirmPartCreation.showAndWait();
        if (result.get() == ButtonType.OK) {

            /**
             * int id
             * This attribute gets the ID that was pre-populated in the ID field
             */

            int id = Integer.parseInt(partIDTxt.getText());

            /**
             * boolean isAlpha
             * This variable is set to false. It will be used later when
             * determining what errors the user has made
             */
            boolean isAlpha = false;

            /**This line gets the part Name entered by the user after confirming that
            *the name field is not empty. If it is empty, an error saying so will be added
            *to the errorMessages ArrayList created earlier*
            */
            if (partNameTxt.getText().isEmpty()) {
                errorMessagesAdd("Name", "empty");
            }
            /**
             *If the Name field is not empty, its contents are transferred to a variable
             * that will be used when creating the part
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
                    errorMessagesAdd("Inv", "empty");
                } else {
                    /**
                     * If the field had a non-numerical string, add an error to errorMessages
                     */
                    errorMessagesAdd("Inv", "mismatch");
                    /**
                     * Set isAlpha variable to true for error reporting purposes
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
                    Integer.parseInt(partOtherTxt.getText()); } catch (Exception f) {
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
                 * Proceed with creating a part if there were no errors
                 */

                /**
                 * If the radio button was set to outsourced, create a new outsourced part
                 * parsing the company name entered from the appropriate field
                 */

            } else if (toggleSource) {
                String companyName = partOtherTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                /**
                 * Send user back to the main screen after part creation
                 */


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                /**
                 * If the radio button was set to inHouse, create a new inHouse part
                 * parisng the machineid entered from the appropriate field
                 */

                int machineid = Integer.parseInt(partOtherTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineid));
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
     * Send id.
     * Method to get the highest partID that exists in allParts array
     * and pre-populates the id field with an ID that is one higher
     */
    public void sendID() {
        int partCount = Inventory.getAllParts().get(Inventory.getAllParts().size()-1).getId();
        partIDTxt.setText((String.valueOf(partCount + 1)));
    }

    /**
     * On click in house.
     * Method to change the text on the form to 'Machine ID' when the appropriate radio button is selected
     *
     * @param action the action
     */
    public void onClickInHouse(ActionEvent action)
    {
        addPartSourceToggle.setText("Machine ID");
        /**
         * Set this variable to false to ensure the correct subclass is created
         */

        toggleSource = false;
    }

    /**
     * On click outsourced.
     * Method to change the text on the form to 'Company Name' when the appropriate radio button is selected
     *
     * @param action the action
     */
    public void onClickOutsourced(ActionEvent action)
    {
        addPartSourceToggle.setText("Company Name");
        /**
         * Set this variable to false to ensure the correct subclass is created
         */


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
