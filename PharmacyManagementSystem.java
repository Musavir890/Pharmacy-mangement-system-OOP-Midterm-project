import java.util.ArrayList;
import java.util.Scanner;


abstract class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }


    public abstract void display();
}


class Medicine extends Product {
    private int quantity;
    private String expiryDate;

    public Medicine(int id, String name, double price, int quantity, String expiryDate) {
        super(id, name, price);
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getExpiryDate() { return expiryDate; }

    @Override
    public void display() {
        System.out.printf("ID: %d | Name: %s | Price: $%.2f | Stock: %d | Expiry: %s\n",
                getId(), getName(), getPrice(), quantity, expiryDate);
    }
}


public class PharmacyManagementSystem {
    private static ArrayList<Medicine> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        System.out.println("--- Welcome to the Pharmacy Management System ---");

        while (!exit) {
            System.out.println("\n1. Add Medicine");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Stock");
            System.out.println("4. Process Sale");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1: addMedicine(); break;
                case 2: viewInventory(); break;
                case 3: updateStock(); break;
                case 4: processSale(); break;
                case 5: exit = true; System.out.println("Exiting System. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addMedicine() {
        System.out.print("Enter Medicine ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Expiry Date (MM/YYYY): ");
        String expiry = scanner.nextLine();

        inventory.add(new Medicine(id, name, price, quantity, expiry));
        System.out.println("Medicine added successfully!");
    }

    private static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is currently empty.");
            return;
        }
        System.out.println("\n--- Current Inventory ---");
        for (Medicine med : inventory) {
            med.display();
        }
    }

    private static void updateStock() {
        System.out.print("Enter Medicine ID to update: ");
        int id = scanner.nextInt();
        for (Medicine med : inventory) {
            if (med.getId() == id) {
                System.out.print("Enter new quantity: ");
                int newQty = scanner.nextInt();
                med.setQuantity(newQty);
                System.out.println("Stock updated successfully!");
                return;
            }
        }
        System.out.println("Medicine ID not found.");
    }

    private static void processSale() {
        System.out.print("Enter Medicine ID to sell: ");
        int id = scanner.nextInt();
        for (Medicine med : inventory) {
            if (med.getId() == id) {
                System.out.print("Enter quantity to sell: ");
                int qtyToSell = scanner.nextInt();

                if (qtyToSell <= med.getQuantity()) {
                    med.setQuantity(med.getQuantity() - qtyToSell);
                    double totalBill = qtyToSell * med.getPrice();
                    System.out.printf("Sale processed! Total Bill: $%.2f\n", totalBill);
                    System.out.println("Remaining Stock: " + med.getQuantity());
                } else {
                    System.out.println("Error: Insufficient stock. Current stock is " + med.getQuantity());
                }
                return;
            }
        }
        System.out.println("Medicine ID not found.");
    }
}