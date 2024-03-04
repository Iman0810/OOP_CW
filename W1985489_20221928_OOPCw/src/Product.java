public abstract class Product implements Comparable<Product> {
    private String productID;
    private String productName;
    private int numOfItem;
    private double price;

    public Product(String productID, String productName, int numOfItem, double price){
        this.productID=productID;
        this.productName=productName;
        this.numOfItem=numOfItem;
        this.price=price;
    }

    public String getProductID(){
        return productID;
    }
    public void setProductID(String productID){
        this.productID=productID;
    }
    public String getProductName(){
        return productName;
    }
    public void setProductName(String productName){
        this.productName=productName;
    }
    public int getNumOfItem(){
        return numOfItem;
    }
    public void setNumOfItem(int numOfItem){
        this.numOfItem=numOfItem;
    }
    public double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price=price;
    }

    @Override
    public int compareTo(Product o) {
        return productID.compareTo(o.productID);
    }

    public String getCategory() {
        if (this instanceof Electronics) {
            return "Electronics";
        } else if (this instanceof Clothing) {
            return "Clothing";
        } else {
            // Handle other product types if needed
            return "Unknown";
        }
    }


}

