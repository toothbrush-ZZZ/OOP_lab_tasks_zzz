package model;

public class Cart {
    //Constants
    public static final int MAX_TICKETS = 20;
    public static final int MAX_ITEMS = 20;
    //Fields
    private final Customer owner;
    private final Ticket[] tickets;
    private int ticketCount;
    private final ConcessionItem[] items;
    private final int[] qtys;
    private int itemCount;

    public Cart(Customer owner){
        this.owner = owner;
        tickets = new Ticket[MAX_TICKETS];
        items = new ConcessionItem[MAX_ITEMS];
        qtys = new int[MAX_ITEMS];

        ticketCount = 0;
        itemCount = 0;
    }

    /*public void addTicket(Ticket t){
        if(ticketCount < MAX_TICKETS){
            tickets[ticketCount] = t;
            ticketCount++;
        }
    }*/
    public boolean addTicket(Ticket t) {
        if (ticketCount >= MAX_TICKETS) return false;
        tickets[ticketCount++] = t;
        return true;
    }

    /*public void addItem(ConcessionItem c, int qty){
        if(itemCount>=MAX_ITEMS || qty <= 0) return;

        items[itemCount] = c;
        qtys[itemCount] = qty;
        itemCount++;
    }*/
    public boolean addItem(ConcessionItem c, int qty) {
        if (qty <= 0)              return false;
        if (itemCount >= MAX_ITEMS) return false;
        items[itemCount]  = c;
        qtys[itemCount]   = qty;
        itemCount++;
        return true;
    }
    
    //getOwner(), getTickets(), getTicketCount(), getItems(), getQtys(), getItemCount().
    public Customer getOwner(){
        return owner;
    }
    public int getTicketCount(){
        return ticketCount;
    }
    
    /*public Ticket[] getTickets(){
        return tickets;
    }
    public ConcessionItem[] getItems(){
        return items;
    }
    public int[] getQtys(){
        return qtys;
    }*/
    // Defensive copies — returns exact-sized array, not the full MAX array
    public Ticket[] getTickets() {
        Ticket[] copy = new Ticket[ticketCount];
        System.arraycopy(tickets, 0, copy, 0, ticketCount);
        return copy;
    }
    
    public ConcessionItem[] getItems() {
        ConcessionItem[] copy = new ConcessionItem[itemCount];
        System.arraycopy(items, 0, copy, 0, itemCount);
        return copy;
    }
    
    public int[] getQtys() {
        int[] copy = new int[itemCount];
        System.arraycopy(qtys, 0, copy, 0, itemCount);
        return copy;
    }
    
    public int getItemCount(){
        return itemCount;
    }

    public double sumTicketsPaid(){
        double sum = 0;
        for(int i=0;i<ticketCount;i++){
            sum+=tickets[i].getPricePaid();
        }
        return sum;
    }

    public double sumConcessionsRaw(){
        double sum = 0;
        for(int i=0;i<itemCount;i++){
            sum+=items[i].getUnitPrice()*qtys[i];
        }
        return sum;
    }

    public boolean hasItem(String code){
        for(int i=0;i<itemCount;i++){
            if(items[i].getCode().equals(code)) return true;
        }
        return false;
    }


}
