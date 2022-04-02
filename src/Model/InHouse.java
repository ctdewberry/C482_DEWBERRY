package Model;

/**
 * The type In house.
 * Class to create 'inHouse' class that inherits from 'part'
 */

public class InHouse extends Part{

    /**
     * create Int machineID for future use
     */
    private int machineid;

    /**
     * Gets machineid.
     *
     * @return the machineid
     */
    public int getMachineid() {
        return machineid;
    }

    /**
     * Sets machineid.
     * method to set machineId, this method is not used as I instead use:
     * Inventory.addPart(new inHouse(id, name, price, stock, min, max, machineId));
     * when creating a part and:
     * Part part = new InHouse(id, name, price, stock, min, max, machineid);
     * and Inventory.updatePart(indexID, part)
     * when modifying a part. I am able to pull the company name from the text field the user has entered.
     * By using updatePart (which uses allParts.set), I am able to avoid creating a duplicate part
     * @param machineid the machineid
     */

    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }

    /**
     * Instantiates a new In house.
     *
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param stock     the stock
     * @param min       the min
     * @param max       the max
     * @param machineid the machineid
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineid) {
        super(id, name, price, stock, min, max);
        this.machineid = machineid;
    }

}
