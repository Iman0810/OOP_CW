
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    ArrayList<Product> listOfProduct= new ArrayList<>();

    //Add product
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("ADD PRODUCT TO THE SYSTEM");

        if(listOfProduct.size() < 50) {
            System.out.println("\nSelect the number of product type: ");
            System.out.println("        1.Electronics");
            System.out.println("        2.Clothing");
            System.out.print("Choice :");
            if (!scanner.hasNextInt()) {
                System.out.println("Only numeric values are allowed.\n");
                return;
            }
            int productType = scanner.nextInt();



            if (productType == 1) {

                scanner.nextLine();
                System.out.print("\nEnter the product ID: ");
                String productId = scanner.nextLine();

                if (productId.isEmpty()) {
                    System.out.println("ProductID cannot be empty.\n");
                    return;
                }
                System.out.print("Enter the product name: ");
                String productName = scanner.nextLine();

                if (!productName.matches("^[a-zA-Z]+$")) {
                    System.out.println("Name can only contain alphabetic characters.\n");
                    return;
                }

                System.out.print("Enter the number of available items: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Only numeric values are allowed.\n");
                    return;
                }
                int numOfItem = scanner.nextInt();

                System.out.print("Enter the product price: ");

                if (!scanner.hasNextDouble()) {
                    System.out.println("Only numeric values are allowed.\n");
                    return;
                }

                double productPrice = scanner.nextDouble();

                scanner.nextLine();
                System.out.print("Enter the brand: ");
                String brand = scanner.nextLine();

                System.out.print("Enter the warranty period: ");
                int warrantyPeriod = scanner.nextInt();

                Electronics electronics = new Electronics(productId, productName, numOfItem, productPrice, brand, warrantyPeriod);
                listOfProduct.add(electronics);

                System.out.println("\n" +numOfItem+" Electronic products added successfully");

            } else if (productType == 2) {

                scanner.nextLine();
                System.out.print("\nEnter the product ID: ");
                String productId = scanner.nextLine();

                if (productId.isEmpty()) {
                    System.out.println("ProductID cannot be empty.\n");
                    return;
                }

                System.out.print("Enter the product name: ");
                String productName = scanner.nextLine();

                if (!productName.matches("^[a-zA-Z]+$")) {
                    System.out.println("Name can only contain alphabetic characters.\n");
                    return;
                }

                System.out.print("Enter the number of available items: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Only numeric values are allowed.\n");
                    return;
                }
                int numOfItem = scanner.nextInt();

                System.out.print("Enter the product price: ");

                if (!scanner.hasNextDouble()) {
                    System.out.println("Only numeric values are allowed.\n");
                    return;
                }

                double productPrice = scanner.nextDouble();

                scanner.nextLine();
                System.out.print("Enter the size: ");
                String size = scanner.nextLine();

                System.out.print("Enter the colour: ");
                String colour = scanner.nextLine();

                Clothing clothing = new Clothing(productId, productName, numOfItem, productPrice, size, colour);
                listOfProduct.add(clothing);

                System.out.println("\n" +numOfItem +" clothing product added successfully");

            } else {
                System.out.println("\nInvalid for product type.Please try again");
            }

        }else {
            System.out.println("\nSystem is full.Cannot add any more product now.Please try again later");
        }
    }
    //delete product
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("DELETE PRODUCT FROM THE SYSTEM");

        System.out.print("What is the product ID: ");
        String productId = scanner.nextLine();
        Product deletingProduct=null;
        for (Product product : listOfProduct){
            if(product.getProductID().equals(productId)){
                deletingProduct=product;
                break;
            }
        }

        if (deletingProduct!=null){
            listOfProduct.remove(listOfProduct.indexOf(deletingProduct));
            System.out.println("\nProduct "+deletingProduct.getProductName()+" Successfully Deleted.");
        }else{
            System.out.println("\nProduct ID NOT FOUND!!!");
        }

    }
    //print product
    public void printProduct() {
        System.out.println("-------------------------------------------");
        System.out.println("LIST OF PRODUCTS IN THE SYSTEM\n");
        Collections.sort(listOfProduct, Comparator.comparing(Product::getProductID));

        for (Product product:listOfProduct){

            if(product instanceof Electronics electronics) {

                System.out.println("Electronic");
                System.out.println("Product ID :" + product.getProductID());
                System.out.println("Product Name :" + product.getProductName());
                System.out.println("Product Amount :" + product.getNumOfItem());
                System.out.println("Product Price :" + product.getPrice());
                System.out.println("Product Brand :" + electronics.getBranch());
                System.out.println("Product Warranty :" + electronics.getWarrantyPeriod()+"\n");

            }else if(product instanceof Clothing clothing) {

                System.out.println("Clothing");
                System.out.println("Product ID :" + product.getProductID());
                System.out.println("Product Name :" + product.getProductName());
                System.out.println("Product Amount :" + product.getNumOfItem());
                System.out.println("Product Price :" + product.getPrice());
                System.out.println("Product Color :" + clothing.getColour());
                System.out.println("Product Size :" + clothing.getSize()+"\n");
            }
        }
    }
    //save file
    public void saveFile() {
        File file = new File("data.csv");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, false));

            for (Product product : listOfProduct) {
                if (product instanceof Electronics electronics) {
                    writer.println("Electronic," +
                            product.getProductID() + "," +
                            product.getProductName() + "," +
                            product.getNumOfItem() + "," +
                            product.getPrice() + "," +
                            electronics.getBranch() + "," +
                            electronics.getWarrantyPeriod());
                } else if (product instanceof Clothing clothing) {
                    writer.println("Clothing," +
                            product.getProductID() + "," +
                            product.getProductName() + "," +
                            product.getNumOfItem() + "," +
                            product.getPrice() + "," +
                            clothing.getColour() + "," +
                            clothing.getSize());
                }
            }

            writer.close();
            System.out.println("File saved successfully.");
        } catch (IOException ex) {
            System.out.println("Invalid Path");
        }
    }
    //load file
    public void loadFile() {
        File file = new File("data.csv");
        listOfProduct.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String productType = tokenizer.nextToken();

                if (productType.equals("Electronic")) {
                    String productId = tokenizer.nextToken().trim();
                    String productName = tokenizer.nextToken().trim();
                    int numOfItem = Integer.parseInt(tokenizer.nextToken().trim());
                    double productPrice = Double.parseDouble(tokenizer.nextToken().trim());
                    String branch = tokenizer.nextToken().trim();
                    int warrantyPeriod = Integer.parseInt(tokenizer.nextToken().trim());

                    Electronics electronics = new Electronics(productId, productName, numOfItem, productPrice, branch, warrantyPeriod);
                    listOfProduct.add(electronics);
                } else if (productType.equals("Clothing")) {
                    String productId = tokenizer.nextToken().trim();
                    String productName = tokenizer.nextToken().trim();
                    int numOfItem = Integer.parseInt(tokenizer.nextToken().trim());
                    double productPrice = Double.parseDouble(tokenizer.nextToken().trim());
                    String colour = tokenizer.nextToken().trim();
                    String size = tokenizer.nextToken().trim();

                    Clothing clothing = new Clothing(productId, productName, numOfItem, productPrice, size, colour);
                    listOfProduct.add(clothing);
                }
            }

            reader.close();
            System.out.println("File loaded successfully.");
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error loading file: " + ex.getMessage());
        }
    }

    public ArrayList<Product> getListOfProduct() {
        return listOfProduct;
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int action;

        loadFile();

        do {
            System.out.println("\n-----------------------------------------");
            System.out.println("          Shopping System Menu          ");
            System.out.println("-----------------------------------------\n");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Exit");

            System.out.print("Enter your action: ");

            action = scanner.nextInt();

            switch (action) {
                case 1:
                    addProduct();
                    saveFile();
                    break;
                case 2:
                    deleteProduct();
                    saveFile();
                    break;
                case 3:
                    printProduct();
                    break;
                case 4:
                    System.out.println("Exit the Program.");
                    break;
                default:
                    System.out.println("Invalid action.Please try again!");
                    break;
            }

        } while (action != 4);
    }
}

