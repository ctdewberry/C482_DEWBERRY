package Model;


import Controller.AddProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Product.
 */
public class Product {


    private ObservableList <Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Product.
     * initialize variables for product
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */

    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

/**
 * the following getters and setters are not used, as i used the data that either transferred to the
 * modify screen for parts and products, or entered by the user in the Add and Modify screen
 * In hind sight, using these may have been a better option, but the method I used is just as
 * accurate and allows me to determine the location in the array of the part or product
 * which is importan for when i am replacing a part or product, or making sure that the
 * ID increments by one when adding a new part or product
 */

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add associated part.
     *
     * @param part the part
     */

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete associated part boolean.
     * removes associated parts from product
     * this method is not used as i opted to create a temporary observable list while the user adds and remove
     * associated products. This temporary list replaces the associatedParts observable list once the user saves
     * the changes or is discarded if they cancel.
     * @param selectedAssociatedPart the selected associated part
     * @return the boolean
     */


    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all associated parts.
     * method used to return a products associated parts list
     * @return the all associated parts
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
