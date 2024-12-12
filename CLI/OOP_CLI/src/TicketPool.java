import static java.awt.Color.BLUE;

public class TicketPool {
    private int ticketsAvailable = 0;
    private int maximumTicketCapacity = 0;
    private int ticketsAdded = 0;


    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public synchronized void addTickets(String vendorName, int ticketsAdded){

        if (this.ticketsAvailable < this.maximumTicketCapacity) {
            this.ticketsAvailable += ticketsAdded;
            System.out.println(Main.BLUE + Main.buffer + vendorName + " added " + ticketsAdded + " ticket(s)" + Main.buffer + Main.RESET);
        }

    }

    public int getMaximumTicketCapacity(){
        return this.maximumTicketCapacity;
    }

    public int getTicketsAvailable(){
        return this.ticketsAvailable;
    }

    public synchronized boolean sellTickets(int ticketsToSell) {
        if (ticketsAvailable >= ticketsToSell) {
            ticketsAvailable -= ticketsToSell;
            System.out.println(Main.BLUE +  Main.buffer + ticketsToSell + " ticket(s) sold. " + ticketsAvailable + " remaining." + Main.buffer + Main.RESET);
            return true;
        } else {
            System.out.println("Not enough tickets available.");
            return false;
        }
    }
}
