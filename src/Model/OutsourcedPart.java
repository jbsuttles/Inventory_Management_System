package Model;

public class OutsourcedPart extends Part {

    private String companyName;

    // Constructor
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    //Set getter
    public String getCompanyName() {
        return companyName;
    }

    //Set setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
