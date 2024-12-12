import java.io.IOException;
import java.net.ServerSocket;

public class Customer implements Runnable{
    private String customerName;
    private String customerId;
    private int ticketsBought = 0;
    private boolean active = true;
    private int purchaseRate;

    public Customer(String customerName, String customerId, int purchaseRate) throws IOException {
        this.customerName = customerName;
        this.customerId = customerId;
        this.purchaseRate = purchaseRate;
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
            int sleepTime = this.purchaseRate * 1000;
            try {
                this.ticketsBought ++;
                System.out.println(Main.BLUE + Main.buffer + "Customer " + customerName + " has bought " + ticketsBought  + " tickets" + Main.buffer + Main.buffer);
                Thread.sleep(sleepTime);
                this.active = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(customerName + " was interrupted.");
                break;
            }
        }
    }
}
