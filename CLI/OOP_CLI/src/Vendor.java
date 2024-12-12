import java.util.Scanner;

public class Vendor implements Runnable {
    private String vendorName;
    private String vendorId;
    private TicketPool ticketPool;
    private boolean active = true;
    private int ticketsByVendor;
    private int releaseRate;
    Scanner scanner = new Scanner(System.in);

    public Vendor(String vendorName, String vendorId, int ReleaseRate) {
        this.vendorName = vendorName;
        this.vendorId = vendorId;
        this.releaseRate = ReleaseRate;
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


    @Override
    public void run() {
        int sleepTime  = this.releaseRate * 1000;
        while (active) {
            try {
                this.ticketPool.addTickets(this.vendorName, 1);
                Thread.sleep(sleepTime);
                this.active = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(vendorName + " was interrupted.");
                this.active = false;
            }
        }

    }
}
