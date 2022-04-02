package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * C482 Final Project
 * @author Caleb Dewberry
 * WGU Student ID: #001222626
 */

/**
 * <b>C482 Section G Part 2:</b>
 * <p>Describe a compatible feature suitable to your application that would extend functionality to the next version if you were to update the application
 * <p>If I were to update this application, I would implement categories for the parts. A simple drop down with a list of various categories on the main
 * page would work. Once you select the appropriate category, the table view would show all items in that category and then when you do a search,
 * it would narrow its search down to only search in that subgroup. This would be especially helpful if someone using software similar to this, had a
 * rather large inventory. This would of course require you to select a category when adding a part, or switch categories when modifying parts.
 * Likewise, I would then expand the category over to the products section.
 */


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainMenu.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        /**
         * Example data has been pre-entered:
         * sample parts
         */

        InHouse part1 = new InHouse(1,"test",10.00,5,1,10,54328);
        InHouse part2 = new InHouse(2,"test2",20.00,6,1,10,123);
        Outsourced part3 = new Outsourced(3, "test 3", 20, 8, 1, 20, "Apple");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        /**
         * Example data has been pre-entered:
         * sample products
         */

        Product prod1 = new Product(1001,"testProd1", 15.50, 5, 1,20);
        Product prod2 = new Product(1002,"testProd2", 17, 5, 1,20);
        Product prod3 = new Product(1003,"testProd3", 20, 5, 1,20);
        Product prod4 = new Product(1004,"testProd4", 20, 5, 1,20);

        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);

        /**
         * Example data has been pre-entered:
         * sample parts associated to products
         */

        prod1.addAssociatedPart(part1);
        prod2.addAssociatedPart(part1);
        prod2.addAssociatedPart(part2);
        prod3.addAssociatedPart(part1);
        prod3.addAssociatedPart(part2);
        prod3.addAssociatedPart(part3);



        launch(args);
    }
}
