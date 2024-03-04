import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ShoppingCart extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel inputPanel2;
    private JComboBox<String> comboBox;
    private JTable table;
    private JTable cartTable;
    private WestminsterShoppingManager shoppingManager;
    private JLabel detailsLabel;
    private JPanel detailsPanel;
    private  JButton button;
    private JButton addToCartButton;
    private int selectedRow;
    private Set<Integer> selectedProductIndices;
    private JLabel totalLabel;
    private double totalValue = 0.0;


    ShoppingCart() {
        cartTable = new JTable(new DefaultTableModel(new String[]{"Product ", "Quantity", "Price"}, 0));
        totalLabel = new JLabel("Total: £0.0");

        JPanel inputPanel1 = new JPanel();
        selectedProductIndices = new HashSet<>();

        String[] productCategory = {"All", "Electronics", "Clothing"};
        comboBox = new JComboBox<>(productCategory);
        // Add this line in the constructor
        comboBox.addActionListener(this);


        JLabel label = new JLabel();
        label.setText("Select product category");


        button = new JButton("Shopping Cart");

        inputPanel1.setLayout(new FlowLayout());
        inputPanel1.add(label);
        inputPanel1.add(comboBox);
        inputPanel1.add(button);

        button.addActionListener(this);

        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        TableModel model = new DefaultTableModel(columnNames, 0);


        inputPanel2 = new JPanel();
        table = new JTable(model);
        table.setEnabled(false);

        table.setRowSelectionAllowed(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

// updateTableData() method to enable sorting on all columns
        sorter.setSortKeys(null);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 150));

        inputPanel2.add(scrollPane);

// Initialize detailsPanel
        detailsPanel = new JPanel(new BorderLayout());


// Creating JPanel to hold both the detailsPanel and the inputPanel3
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(detailsPanel, BorderLayout.CENTER);

// Add the Add to Shopping Cart button
        JPanel inputPanel3 = new JPanel(new FlowLayout());
        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(this);
        inputPanel3.add(addToCartButton);

        bottomPanel.add(inputPanel3, BorderLayout.SOUTH);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel1, BorderLayout.NORTH);
        mainPanel.add(inputPanel2, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);


        shoppingManager = new WestminsterShoppingManager();

        shoppingManager.loadFile();

        updateTableData();


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.rowAtPoint(e.getPoint());
                displayDetails(selectedRow);
                changeSelectedRowColor(selectedRow);
            }
        });


        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void changeSelectedRowColor(int rowIndex) {
        table.clearSelection();
        table.addRowSelectionInterval(rowIndex, rowIndex);
    }


    private boolean hasAtLeastThreeInCart(DefaultTableModel cartModel) {
        return false;
    }


    public String getCategoryByProductId(String productId) {
        for (Product product : shoppingManager.getListOfProduct()) {



            if (Objects.equals(product.getProductID(), productId)) {
                // Assuming you have a getCategory method in your Product class
                return product.getCategory();
            }
        }
        // Return a default value or handle the case where the product is not found
        return "Unknown";
    }


    public void actionPerformed(ActionEvent e) {
        updateTableData();

        if (e.getSource() == button) {
            openShoppingCart();
        } else if (e.getSource() == addToCartButton) {
            selectedProductIndices.add(selectedRow);
            addToCart();
        } else if (e.getSource() == comboBox) {
            updateTableData();
        }
    }


    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String selectedCategory = (String) comboBox.getSelectedItem();

        for (Product product : shoppingManager.getListOfProduct()) {
            if ("All".equals(selectedCategory) || getCategoryDetails(product).equals(selectedCategory)) {
                Object[] rowData;
                if (product instanceof Electronics electronics) {
                    rowData = new Object[]{
                            product.getProductID(),
                            product.getProductName(),
                            "Electronics",
                            product.getPrice(),
                            electronics.getBranch() + ", " + electronics.getWarrantyPeriod()
                    };
                } else if (product instanceof Clothing clothing) {
                    rowData = new Object[]{
                            product.getProductID(),
                            product.getProductName(),
                            "Clothing",
                            product.getPrice(),
                            clothing.getColour() + ", " + clothing.getSize()
                    };
                } else {
                    // Handle other product types if needed
                    continue;
                }
                model.addRow(rowData);
            }
        }
    }

    private void displayDetails(int rowIndex) {
        if (rowIndex >= 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // to get the selected product index based on the row index

            // Get the selected product from the shoppingManager
            Product selectedProduct = shoppingManager.getListOfProduct().get(rowIndex);
            selectedRow = rowIndex;
            // Display all details for the selected product
            String details = "Product ID: " + selectedProduct.getProductID() + "\n" +
                    "Name: " + selectedProduct.getProductName() + "\n" +
                    "Category: " + getCategoryDetails(selectedProduct) + "\n" +
                    "Price: " + selectedProduct.getPrice() + "\n" +
//                    "Available Quantity: " + selectedProduct.getAvailableQuantityDetails() + "\n" +
                    getAdditionalDetails(selectedProduct);

            JTextArea detailsTextArea = new JTextArea(details);
            detailsTextArea.setEditable(false);
            detailsTextArea.setLineWrap(true);
            detailsTextArea.setWrapStyleWord(true);

            // Creating a JScrollPane to hold the JTextArea
            JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);

            // Clearing the detailsPanel and add the new detailsScrollPane
            detailsPanel.removeAll();
            detailsPanel.add(detailsScrollPane);

            // Repaint and revalidate the detailsPanel to update the display
            detailsPanel.repaint();
            detailsPanel.revalidate();
        }
    }

    private String getCategoryDetails(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            // Handle other product types if needed
            return "Unknown";
        }
    }

    private String getAdditionalDetails(Product product) {
        if (product instanceof Electronics electronics) {
            return "Branch: " + electronics.getBranch() + "\nWarranty Period: " + electronics.getWarrantyPeriod();
        } else if (product instanceof Clothing clothing) {
            return "Colour: " + clothing.getColour() + "\nSize: " + clothing.getSize();
        } else {
            // Handle other product types if needed
            return "";
        }
    }


    private void openShoppingCart() {
        // Create a new JFrame for the shopping cart window
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");

        // Set the layout manager to BorderLayout
        shoppingCartFrame.setLayout(new BorderLayout());

        String[] cartColumnNames = {"Product ", "Quantity", "Price"};

        // Using the existing cartTable created as a class-level variable
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setPreferredSize(new Dimension(500, 150));

        // Add the shopping cart table to the shopping cart window
        shoppingCartFrame.add(cartScrollPane, BorderLayout.CENTER);
        shoppingCartFrame.add(totalLabel, BorderLayout.SOUTH);

        // Customizing the shopping cart window as needed
        shoppingCartFrame.setSize(600, 300);
        shoppingCartFrame.setLocationRelativeTo(null);
        shoppingCartFrame.setVisible(true);
    }

    private void addToCart() {
        double discount = 0.0;

        if (!selectedProductIndices.isEmpty()) {
            DefaultTableModel mainTableModel = (DefaultTableModel) table.getModel();
            DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();

            for (Integer selectedProductIndex : selectedProductIndices) {
                // Get the selected product directly from the shopping manager
                Product selectedProduct = shoppingManager.getListOfProduct().get(selectedProductIndex);

                // to Check if the product is already in the cart
                boolean productExistsInCart = false;
                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    String productNameInCart = (String) cartModel.getValueAt(i, 0);
                    if (productNameInCart.equals(selectedProduct.getProductName())) {
                        // Product already in the cart, increase quantity and update price
                        int currentQuantity = (int) cartModel.getValueAt(i, 1);
                        double currentPrice = (double) cartModel.getValueAt(i, 2);

                        cartModel.setValueAt(currentQuantity + 1, i, 1);  // Increment quantity
                        cartModel.setValueAt(currentPrice + selectedProduct.getPrice(), i, 2);  // Update price
                        productExistsInCart = true;
                        break;
                    }
                }

                // If the product is not in the cart, add it
                if (!productExistsInCart) {
                    Object[] rowData = new Object[]{
                            selectedProduct.getProductName(),
                            1, // Assuming initial quantity is 1, modify as needed
                            selectedProduct.getPrice()
                    };
                    cartModel.addRow(rowData);
                }

                // Updating the total value
                totalValue += selectedProduct.getPrice();
            }

            // Check for the 20% discount condition
            if (hasAtLeastThreeInCart(cartModel)) {
                discount = totalValue * 0.20; // Apply 20% discount
            }

            selectedProductIndices.clear();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart.", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }

        // Update and display the total value with discount
        totalValue -= discount;
        totalLabel.setText("Discount: " + String.format("%.2f", discount) + "Total: £" + String.format("%.2f", totalValue));
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.updateTableData(); // Call updateTableData to initialize the table

        });
    }

}