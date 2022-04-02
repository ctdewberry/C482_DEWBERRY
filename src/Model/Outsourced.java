package Model;

/**
 * The type Outsourced.
 * Class to create 'outsourced' class that inherits from 'part'
 */

public class Outsourced extends Part{

    /**
     * create String variable companyName for future use
     */
    private String companyName;

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name.
     * method to set companyName, this method is not used as I instead use:
     * Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
     * when creating a part and:
     * Part part = new Outsourced(id, name, price, stock, min, max, companyName);
     * and Inventory.updatePart(indexID, part)
     * when modifying a part. I am able to pull the company name from the text field the user has entered.
     * By using updatePart (which uses allParts.set), I am able to avoid creating a duplicate part
     * @param companyName the company name
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Instantiates a new Outsourced.
     *
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param stock       the stock
     * @param min         the min
     * @param max         the max
     * @param companyName the company name
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}
