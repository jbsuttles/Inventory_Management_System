package Model;

public class InhousePart extends Part {

    private int machineId;

    // Constructor
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // Set getter
    public int getMachineId() {
        return machineId;
    }

    // Set setter
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

