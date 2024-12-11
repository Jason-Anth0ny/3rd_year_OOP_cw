public class TicketPool {
    private int ticketsAvailable = 0;
    private int maximumTicketCapacity;
    private int ticketsAdded = 0;


    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public synchronized void addTickets(String vendorName, int ticketsAdded){

        this.ticketsAvailable += ticketsAdded;
        System.out.println(vendorName + " added " + ticketsAdded + " tickets");
    }

    public int getMaximumTicketCapacity(){
        return maximumTicketCapacity;
    }

    public int getTicketsAvailable(){
        return ticketsAvailable;
    }

    public int getTicketsAdded() {
        return this.ticketsAdded;
    }

    public synchronized boolean sellTickets(int ticketsToSell) {
        if (ticketsAvailable >= ticketsToSell) {
            ticketsAvailable -= ticketsToSell;
            System.out.println(ticketsToSell + " tickets sold. " + ticketsAvailable + " remaining.");
            return true;
        } else {
            System.out.println("Not enough tickets available.");
            return false;
        }
    }
}
