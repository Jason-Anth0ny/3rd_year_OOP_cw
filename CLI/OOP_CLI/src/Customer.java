import java.io.IOException;
import java.net.ServerSocket;

public class Customer implements Runnable{
    private String customerName;
    private String customerId;
    private int ticketsBought = 0;
    private ServerSocket serverSocket;
    private boolean active = true;

    public Customer(String customerName, String customerId) throws IOException {
        this.customerName = customerName;
        this.customerId = customerId;

        this.serverSocket =  new ServerSocket(0);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(int ticketsBought) {
        this.ticketsBought += ticketsBought;
    }

    @Override
    public void run() {
        while (active) {
            try {
                var clientSocket = serverSocket.accept();
                System.out.println("Connection accepted on port " + serverSocket.getLocalPort());

                clientSocket.close();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(customerName + " was interrupted.");
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
