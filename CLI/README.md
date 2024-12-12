# 3rd_year_OOP_cw
A customer and vendor based ticketing system

## Configurations

* MaxTicketCapacity - This is set by the admin user when they configure the system, usually before handing it over to the clients. These tickets are distributed among vendors, and are in turn bought by customers.
* TotalTickets - This is the number of tickets that are currently available in the system. Starting value is equal to MaxTicketCapacity and it reduces each time customers make a purchase.
* CustomerRetrievalRate - Shows how often customers can purchase tickets. Sort of like the cooldown time.
* TicketReleaseRate - Shows how often tickets are restocked by vendors.

----

## CLI

- The system requests the user to set the above four configurations. This is the only part of the program the user can influence. Once the user enters the configurations, vendors and customers will be dyanamically created and carry out their functions i.e. releasing tickets and buying tickets respectively. 
- Once all the tickets are sold, customer and vendor threads would all be closed and the system will terminate. 
- This program is meant to be a simulation for the application that has been built, meant to show the most basic functionality of a ticketing platform.