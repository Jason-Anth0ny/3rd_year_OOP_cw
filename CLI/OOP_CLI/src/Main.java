//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//class MultiThreadingImplement implements Runnable{
//    public void run(){}
//}

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main{
    static ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    static ArrayList<Customer> customers = new ArrayList<>();
    private static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (true) {
            try {
                System.out.println("Enter maximum ticket capacity: ");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                log.log(Level.SEVERE, "Invalid input", e);
            }
            break;
        }
        int maximumTicketCapacity = scanner.nextInt();
        scanner.nextLine();
        TicketPool ticketPool = new TicketPool(maximumTicketCapacity);

        while (isRunning) {
            System.out.println("What would you like to do?");
            System.out.println("(1) Create Vendor | (2) Create Customer | (3) Buy tickets | (4) Exit");
            int command = scanner.nextInt();
            scanner.nextLine();

            switch (command) {
                case 1:
                    System.out.println("Enter vendor name: ");
                    String vendorName = scanner.nextLine();

                    String vendorId = "V" + (vendors.size() + 1);

                    Vendor newVendor = new Vendor(vendorName, vendorId);
                    newVendor.setTicketPool(ticketPool);
                    Thread vendorThread = new Thread(newVendor);
                    vendorThread.start();
                    newVendor.setTicketsByVendor();
                    vendors.add(newVendor);
                    log.log(Level.INFO, "Vendor " + vendorName + " created on port " + newVendor.getServerSocket().getLocalPort());
                    newVendor.run();
                    break;

                case 2:
                    System.out.println("Enter customer name: ");
                    String customerName = scanner.nextLine();

                    String customerId = "C" + (customers.size() + 1);

                    Customer newCustomer = new Customer(customerName, customerId);
                    Thread customerThread = new Thread(newCustomer);
                    customerThread.start();
                    customers.add(newCustomer);
                    log.log(Level.INFO, "Customer " + customerName + " created on port " + newCustomer.getServerSocket().getLocalPort());
                    newCustomer.run();
                    break;

                case 3:
                    System.out.println("Enter customer name: "); // to choose the customer who's buying
                    customerName = scanner.nextLine();

                    Customer transactionCustomer = getCustomer(customerName);

                    System.out.println("Enter the vendor you want to buy from: "); // to choose the vendor the customer is buying from
                    vendorName = scanner.nextLine();

                    Vendor transactionVendor = getVendor(vendorName);

                    System.out.println("How many tickets do you want to buy? : ");
                    int ticketsToBuy = scanner.nextInt();
                    scanner.nextLine();

                    TicketPool tickets = transactionVendor.getTicketPool();
                    boolean ticketsSold = tickets.sellTickets(ticketsToBuy);

                    if (ticketsSold == true) {
                        transactionCustomer.setTicketsBought(ticketsToBuy);
                        log.log(Level.INFO, customerName + "bought" + ticketsToBuy + " tickets from " + vendorName + "/n" + transactionVendor.getTicketPool().getTicketsAdded() + "tickets available");
                    } else {
                        log.log(Level.INFO, "Insufficient tickets in stock. Check again later.");
                    }
                    break;

                case 4:
                    isRunning = false;
                    closeCustomers(customers);
                    closeVendors(vendors);
                    scanner.close();
                    break;

                default:
                    System.out.println("Invalid command");
                    break;

            }
            System.out.println("Tickets available: " + ticketPool.getTicketsAvailable());
        }
    }

    // used to retrieve a customer from the list of customers
    public static Customer getCustomer(String customerName)  {
        try {
            for (int i=0; i < customers.size(); i++ ) {
                if (customerName.equalsIgnoreCase(customers.get(i).getCustomerName())) {
                    return customers.get(i);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Customer " + customerName + " not found");
            log.log(Level.SEVERE, e.getMessage());
        }
        return null;

    }

    // used to retrieve a vendor using the vendor name from the list of vendors
    public static Vendor getVendor(String vendorName)  {
        try {
            for (int i=0; i < vendors.size(); i++ ) {
                if (vendorName.equalsIgnoreCase(vendors.get(i).getVendorName())) {
                    return vendors.get(i);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Vendor " + vendorName + " not found");
            log.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    // to close all the vendor threads
    public static void closeVendors(ArrayList<Vendor> vendors) {
        for (int i=0; i < vendors.size(); i++ ) {
            vendors.get(i).setActive(false);
        }
    }

    // to close all the customer threads
    public static void closeCustomers(ArrayList<Customer> customers) {
        for (int i=0; i < customers.size(); i++ ) {
            customers.get(i).setActive(false);
        }
    }
}