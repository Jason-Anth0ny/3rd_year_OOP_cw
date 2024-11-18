import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Vendor implements Runnable {
    private String vendorName;
    private String vendorId;
    private TicketPool ticketPool;
    private ServerSocket serverSocket;
    private boolean active = true;
    private int ticketsByVendor;
    Scanner scanner = new Scanner(System.in);

    public Vendor(String vendorName, String vendorId) throws IOException {
        this.vendorName = vendorName;
        this.vendorId = vendorId;

        this.serverSocket = new ServerSocket(0);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public TicketPool getTicketPool() {
        return this.ticketPool;
    }

    public void setTicketsByVendor() {
        while (true) {
            System.out.println("Enter number of tickets: ");
            int numberOfTickets = scanner.nextInt();
            scanner.nextLine();

            if (this.ticketPool.getTicketsAdded() + numberOfTickets < ticketPool.getMaximumTicketCapacity()) {
                this.ticketsByVendor = numberOfTickets;
                this.ticketPool.addTickets(vendorName, ticketsByVendor);
                System.out.println("Tickets added by vendor: " + vendorName);
                System.out.println("Total tickets: " + this.ticketPool.getTicketsAvailable());
                break;
            } else {
                System.out.println("Sorry, MaximumTicketCapacity exceeded, try again with a smaller amount");
            }
        }
    }

    @Override
    public void run() {
        while (active) {
            try {
                var clientSocket = serverSocket.accept();
                System.out.println("Connection accepted on port " + serverSocket.getLocalPort());
                clientSocket.close();
                ticketPool.addTickets(this.vendorName, 1);
                Thread.sleep(7000);
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendorName + " was interrupted.");
                break;
            }
        }

    }
}
