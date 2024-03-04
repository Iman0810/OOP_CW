import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class Main {

    private static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    public static void main(String[] args) {


        while(true) {
            System.out.print("""
                    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                 Westminster Shopping Manager
                    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    
                    1) Admin Panel
                    2) User GUI
                                    
                    option:""");
            Scanner scanner = new Scanner(System.in);

            if (!scanner.hasNextDouble()) {
                System.out.println("Only numeric values are allowed.\n");
                return;
            }
            int option = scanner.nextInt();


            if (!(option < 3 && option > 0)) {
                System.out.println("Invalid Input Try Again!");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println();
                    westminsterShoppingManager.displayMenu();
                    break;
                case 2:
                    ShoppingCart frame = new ShoppingCart();
                    frame.setTitle("Westminster Shopping Centre");
                    frame.setSize(800,400);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    break;
            }
        }
    }
}