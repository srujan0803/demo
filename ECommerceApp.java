import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Product classes
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: $" + price;
    }
}

// Customer class
class Customer {
    private int id;
    private String name;
    private String email;
    private String password;

    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}

// Order class
class Order {
    private int orderId;
    private Customer customer;
    private List<Product> productList;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void viewOrderDetails() {
        System.out.println("\nOrder Details:");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Products in order:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}

// Main application class
public class ECommerceApp {
    private static List<Product> productCatalog = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static int orderCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeCatalog();

        while (true) {
            System.out.println("\nWelcome to the E-Commerce Application!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;
                case 2:
                    loginCustomer(scanner);
                    break;
                case 3:
                    System.out.println("\nExiting the application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }

    private static void initializeCatalog() {
        productCatalog.add(new Product(1, "Laptop", 1200.00));
        productCatalog.add(new Product(2, "Smartphone", 800.00));
        productCatalog.add(new Product(3, "Headphones", 150.00));
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.print("\nEnter your name: ");
        String name = scanner.next();
        System.out.print("Enter your email: ");
        String email = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        customers.add(new Customer(customers.size() + 1, name, email, password));
        System.out.println("\nCustomer registered successfully!");
    }

    private static void loginCustomer(Scanner scanner) {
        System.out.print("\nEnter your email: ");
        String email = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        for (Customer customer : customers) {
            if (customer.getEmail().equals(email) && customer.validatePassword(password)) {
                System.out.println("\nLogin successful!");
                customerMenu(scanner, customer);
                return;
            }
        }
        System.out.println("\nInvalid email or password.");
    }

    private static void customerMenu(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Place an Order");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    placeOrder(scanner, customer);
                    break;
                case 3:
                    System.out.println("\nLogging out...");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void viewProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : productCatalog) {
            System.out.println(product);
        }
    }

    private static void placeOrder(Scanner scanner, Customer customer) {
        Order order = new Order(orderCounter++, customer);
        while (true) {
            System.out.print("\nEnter the Product ID to order (enter 0 to finish): ");
            int productId = scanner.nextInt();

            if (productId == 0) {
                break;
            }

            Product product = findProductById(productId);
            if (product != null) {
                order.addProduct(product);
                System.out.println("\nProduct added to order.");
            } else {
                System.out.println("\nInvalid Product ID.");
            }
        }

        if (!order.productList.isEmpty()) {
            orders.add(order);
            System.out.println("\nOrder placed successfully!");
            order.viewOrderDetails();
        } else {
            System.out.println("\nNo products were added to the order.");
        }
    }

    private static Product findProductById(int productId) {
        for (Product product : productCatalog) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
}
