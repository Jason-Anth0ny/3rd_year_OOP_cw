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

import static java.util.logging.Logger.global;

public class Main{
    static ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    static ArrayList<Customer> customers = new ArrayList<>();
    private static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String buffer = "==========";

    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int maximumTicketCapacity;
        while (true) {
            try {
                System.out.println("Enter maximum ticket capacity: ");
                maximumTicketCapacity = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type");
                scanner.nextLine();
            }
        }
        log.log(Level.INFO, "Maximum ticket capacity set as "+maximumTicketCapacity);
        final TicketPool ticketPool = new TicketPool(maximumTicketCapacity);
        log.log(Level.INFO, "Ticket pool initialized");

        int releaseRate;
        while (true) {
            try {
                System.out.println("Enter ticket release rate (in seconds and less than 10): ");
                releaseRate = scanner.nextInt();
                scanner.nextLine();
                if (releaseRate >= 10) {
                    System.out.println("Invalid input: Rate must be less than 10");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type ");
                scanner.nextLine();
            }
        }
        log.log(Level.INFO, "Ticket Release Rate set as "+releaseRate);
        log.log(Level.INFO, "Starting vendor creation");

        int purchaseRate;
        while (true) {
            try {
                System.out.println("Enter ticket purchase rate (in seconds and less than 10): ");
                purchaseRate = scanner.nextInt();
                scanner.nextLine();
                if (purchaseRate >= 10) {
                    System.out.println("Invalid input: Rate must be less than 10");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type ");
                scanner.nextLine();
            }
        }

        // creating vendors and adding them to the array
        Vendor vendor1 = new Vendor("Vendor 1", "V1", releaseRate);
        vendors.add(vendor1);
        Vendor vendor2 = new Vendor("Vendor 2", "V2", releaseRate);
        vendors.add(vendor2);
        Vendor vendor3 = new Vendor("Vendor 3", "V3", releaseRate);
        vendors.add(vendor3);
        Vendor vendor4 = new Vendor("Vendor 4", "V4", releaseRate);
        vendors.add(vendor4);
        log.log(Level.INFO, "Vendor list initialized");

        //assignging each of them to the central ticketpool
        for (Vendor v : vendors) {
            v.setTicketPool(ticketPool);
        }

        log.log(Level.INFO, "Commencing the addition of tickets");
        while (ticketPool.getTicketsAvailable() < ticketPool.getMaximumTicketCapacity()) {
            for (Vendor v : vendors) {
                v.setActive(true);
            }
            for (Vendor v : vendors) {
                //each vendor opens their own thread and adds ticket(s) and then closes the thread
                v.run();
            }
        }
        log.log(Level.INFO, "Tickets added successfully");

        //creating customers and adding each one to the customers array
        log.log(Level.INFO, "Starting customer creation");
        Customer customer1 = new Customer("Customer 1", "C1", purchaseRate);
        customers.add(customer1);
        Customer customer2 = new Customer("Customer 2", "C2", purchaseRate);
        customers.add(customer2);
        Customer customer3 = new Customer("Customer 3", "C3", purchaseRate);
        customers.add(customer3);
        Customer customer4 = new Customer("Customer 4", "C4", purchaseRate);
        customers.add(customer4);
        Customer customer5 = new Customer("Customer 5", "C5", purchaseRate);
        customers.add(customer5);
        log.log(Level.INFO, "Customer list initialized");

        log.log(Level.INFO, "Commencing purchase of tickets");
        while (ticketPool.getTicketsAvailable() > 0) {
            for (Customer c : customers) {
                c.setActive(true);
            }
            for (Customer c : customers) {
                if (ticketPool.getTicketsAvailable() > 0) {
                    c.run();
                    ticketPool.sellTickets(1);
                }
            }
        }

        System.out.println("Tickets sold out!!");
        log.log(Level.INFO, "Tickets sold out. Ending simulation");
    }
}