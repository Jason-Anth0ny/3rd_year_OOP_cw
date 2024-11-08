public class TicketPool {
    private String ticketPoolId;
    private final int maxTicketCount;
    private Vendor vendor;
    private int ticketsAvailable;

    public TicketPool(int ticketCount, String ticketPoolId, Vendor vendor, int ticketsAvailable) {
        this.maxTicketCount = ticketCount;
        this.ticketPoolId = ticketPoolId;
        this.vendor = vendor;
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getTicketPoolId() {
        return ticketPoolId;
    }

    public void setTicketPoolId(String ticketPoolId) {
        this.ticketPoolId = ticketPoolId;
    }

    public int getMaxTicketCount() {
        return maxTicketCount;
    }



    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void sellTickets(int ticketsSold) {
        this.ticketsAvailable -= ticketsSold;
    }
}
