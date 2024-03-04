public class Electronics extends Product {
    private String branch;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int numOfItem, double price,String branch, int warrantyPeriod){
        super(productID, productName, numOfItem,price);
        this.branch=branch;
        this.warrantyPeriod=warrantyPeriod;
    }
    public String getBranch(){
        return branch;
    }

    public String getCategory() {
        return "Electronics";
    }

    public void setBranch(String branch){
        this.branch=branch;
    }
    public int getWarrantyPeriod(){
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(int warrantyPeriod){
        this.warrantyPeriod=warrantyPeriod;
    }

}

