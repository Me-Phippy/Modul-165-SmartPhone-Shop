package org;

import com.mongodb.client.MongoClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public class SmartphoneShopAdminApp {
    private static MongoClient mongoClient;
    private static SmartphoneRepository smartphoneRepository;
    private static CustomerRepository customerRepository;
    private static OrderRepository orderRepository;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            mongoClient = MongoDBConfig.createMongoClient();
            String databaseName = MongoDBConfig.getDatabaseName();
            smartphoneRepository = new SmartphoneRepository(mongoClient, databaseName);
            customerRepository = new CustomerRepository(mongoClient, databaseName);
            orderRepository = new OrderRepository(mongoClient, databaseName, customerRepository, smartphoneRepository);
            scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. Manage Smartphones");
                System.out.println("2. Manage Customers");
                System.out.println("3. Manage Orders");
                System.out.println("0. Exit");
                System.out.print("Choose a category: ");

                int mainChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (mainChoice) {
                    case 1:
                        manageSmartphones();
                        break;
                    case 2:
                        manageCustomers();
                        break;
                    case 3:
                        manageOrders();
                        break;
                    case 0:
                        System.out.println("Exiting application...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } finally {
            if (mongoClient != null) mongoClient.close();
            if (scanner != null) scanner.close();
        }
    }

    private static void manageSmartphones() {
        while (true) {
            System.out.println("\n--- Smartphone Management ---");
            System.out.println("1. List Smartphones");
            System.out.println("2. Add Smartphone");
            System.out.println("3. Update Smartphone");
            System.out.println("4. Delete Smartphone");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listSmartphones();
                    break;
                case 2:
                    addSmartphone();
                    break;
                case 3:
                    updateSmartphone();
                    break;
                case 4:
                    deleteSmartphone();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listSmartphones() {
        List<Smartphone> smartphones = smartphoneRepository.findAll();
        if (smartphones.isEmpty()) {
            System.out.println("No smartphones found.");
            return;
        }
        smartphones.forEach(s -> System.out.println("ID: " + s.getId() + " | " + s));
    }

    private static void addSmartphone() {
        Smartphone smartphone = new Smartphone();
        System.out.print("Enter Brand: ");
        smartphone.setBrand(scanner.nextLine());

        System.out.print("Enter Model: ");
        smartphone.setModel(scanner.nextLine());

        System.out.print("Enter Price (€): ");
        smartphone.setPrice(new BigDecimal(scanner.nextLine()));

        System.out.print("Enter RAM (GB): ");
        smartphone.setRamGB(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Screen Size (inches): ");
        smartphone.setScreenSizeInches(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter Storage (GB): ");
        smartphone.setStorageGB(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter OS: ");
        smartphone.setOperatingSystem(scanner.nextLine());

        System.out.print("Enter OS Version: ");
        smartphone.setOsVersion(scanner.nextLine());

        System.out.print("Enter Resolution: ");
        smartphone.setResolution(scanner.nextLine());

        System.out.print("Enter Processor Cores: ");
        smartphone.setProcessorCores(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Battery Capacity (mAh): ");
        smartphone.setBatteryCapacityMAh(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Connectivity (comma-separated): ");
        smartphone.setConnectivity(List.of(scanner.nextLine().split(",")));

        System.out.print("Enter Mobile Standard: ");
        smartphone.setMobileStandard(scanner.nextLine());

        smartphoneRepository.insert(smartphone);
        System.out.println("Smartphone added successfully!");
    }

    private static void updateSmartphone() {
        listSmartphones();
        System.out.print("Enter Smartphone ID to update: ");
        String id = scanner.nextLine();
        Smartphone smartphone = smartphoneRepository.findById(id);
        if (smartphone == null) {
            System.out.println("Smartphone not found.");
            return;
        }

        System.out.print("Enter new Brand (or press Enter to skip): ");
        String brand = scanner.nextLine();
        if (!brand.isEmpty()) smartphone.setBrand(brand);

        System.out.print("Enter new Model (or press Enter to skip): ");
        String model = scanner.nextLine();
        if (!model.isEmpty()) smartphone.setModel(model);

        System.out.print("Enter new Price (or press Enter to skip): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) smartphone.setPrice(new BigDecimal(priceInput));

        smartphoneRepository.update(smartphone);
        System.out.println("Smartphone updated successfully!");
    }

    private static void deleteSmartphone() {
        listSmartphones();
        System.out.print("Enter Smartphone ID to delete: ");
        String id = scanner.nextLine();
        if (smartphoneRepository.delete(id)) {
            System.out.println("Smartphone deleted successfully!");
        } else {
            System.out.println("Smartphone not found.");
        }
    }

    private static void manageCustomers() {
        while (true) {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. List Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listCustomers();
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        customers.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c));
    }

    private static void addCustomer() {
        Customer customer = new Customer();
        System.out.print("Enter First Name: ");
        customer.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        customer.setLastName(scanner.nextLine());

        System.out.print("Enter Email: ");
        customer.setEmail(scanner.nextLine());

        System.out.print("Enter Phone: ");
        customer.setMobilePhone(scanner.nextLine());

        customerRepository.insert(customer);
        System.out.println("Customer added successfully!");
    }

    private static void updateCustomer() {
        listCustomers();
        System.out.print("Enter Customer ID to update: ");
        String id = scanner.nextLine();
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter new First Name (or press Enter to skip): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) customer.setFirstName(firstName);

        System.out.print("Enter new Last Name (or press Enter to skip): ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) customer.setLastName(lastName);

        System.out.print("Enter new Email (or press Enter to skip): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) customer.setEmail(email);

        System.out.print("Enter new Phone (or press Enter to skip): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) customer.setMobilePhone(phone);

        customerRepository.update(customer);
        System.out.println("Customer updated successfully!");
    }

    private static void deleteCustomer() {
        listCustomers();
        System.out.print("Enter Customer ID to delete: ");
        String id = scanner.nextLine();
        if (customerRepository.delete(id)) {
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void manageOrders() {
        while (true) {
            System.out.println("\n--- Order Management ---");
            System.out.println("1. List Orders");
            System.out.println("2. Add Order");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    updateOrder();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            System.out.println("No Orders found.");
            return;
        }
        orders.forEach(s -> System.out.println("ID: " + s.getId() + " | " + s));
    }

    private static void addOrder() {
        listCustomers();
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerRepository.findById(customerId);
        if (customer
                == null) {
            System.out.println("Customer not found.");
            return;
        }

        listSmartphones();
        System.out.print("Enter Smartphone ID: ");
        String smartphoneId = scanner.nextLine();
        Smartphone smartphone = smartphoneRepository.findById(smartphoneId);
        if (smartphone == null) {
            System.out.println("Smartphone not found.");
            return;
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>());


        order.calculateTotal();
        orderRepository.insert(order);
        System.out.println("Order added successfully!");
    }

    private static void updateOrder() {
        listOrders();
        System.out.print("Enter Order ID to update: ");
        String id = scanner.nextLine();
        Order order = orderRepository.findById(id);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        listSmartphones();
        System.out.print("Enter new Smartphone ID (or press Enter to skip): ");
        String smartphoneId = scanner.nextLine();
        if (!smartphoneId.isEmpty()) {
            Smartphone smartphone = smartphoneRepository.findById(smartphoneId);
            if (smartphone == null) {
                System.out.println("Smartphone not found.");
                return;
            }
            order.getItems().clear();
            order.calculateTotal();
        }

        orderRepository.update(order);
        System.out.println("Order updated successfully!");
    }

    private static void deleteOrder() {
        listOrders();
        System.out.print("Enter Order ID to delete: ");
        String id = scanner.nextLine();
        if (orderRepository.delete(id)) {
            System.out.println("Order deleted successfully!");
        } else {
            System.out.println("Order not found.");
        }
    }
}