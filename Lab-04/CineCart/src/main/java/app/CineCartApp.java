package app;

import io.CsvLoader;
import model.*;
import data.*;
import engine.CheckoutEngine;

public class CineCartApp {
    
    public static void main(String[] args) {
        
        // 1. Load all data from CSV files
        Movie[]        movies    = CsvLoader.loadMovies("movies.csv");
        Hall[]         halls     = CsvLoader.loadHalls("halls.csv");
        ShowtimeBoard  board     = CsvLoader.loadShowtimes("showtimes.csv", movies, halls);
        ConcessionMenu menu      = CsvLoader.loadConcessions("concessions.csv");
        Customer[]     customers = CsvLoader.loadCustomers("customers.csv");
        
        System.out.println("=== CineCart Booking System ===\n");
        
        // 2. Show available showtimes
        System.out.println("--- Available Showtimes ---");
        board.displayAll();
        
        // 3. Show seat layout of Hall 2
        System.out.println("\n--- Hall 2 Seat Layout (. = available, * = premium, # = booked) ---");
        halls[1].displayLayout();
        
        // 4. Pick Alice (customer id=1, GOLD tier) and create her cart
        Customer alice = customers[0];
        System.out.println("\n--- Customer ---");
        System.out.println(alice);
        
        Cart cart = new Cart(alice);
        
        // 5. Create the engine
        CheckoutEngine engine = new CheckoutEngine(board, menu);
        
        // 6. Book tickets
        System.out.println("\n--- Booking Tickets ---");
        
        // Book showtime 17 (Inception, peak, hall 2, rows 0 = premium)
        System.out.println("Booking T17 seat [0,0]: " + engine.bookTicket(cart, 17, 0, 0));
        System.out.println("Booking T17 seat [0,1]: " + engine.bookTicket(cart, 17, 0, 1));
        
        // Try booking same seat twice (should fail)
        System.out.println("Booking T17 seat [0,0] again: " + engine.bookTicket(cart, 17, 0, 0));
        
        // Try a non-existent showtime (should fail)
        System.out.println("Booking unknown showtime 99: " + engine.bookTicket(cart, 99, 0, 0));
        
        // Try Bob (age 16) booking The Godfather (R, min age 18) — should fail
        Customer bob = customers[1];
        Cart bobCart = new Cart(bob);
        System.out.println("Bob booking T18 (Godfather R): " + engine.bookTicket(bobCart, 18, 0, 0));
        
        // 7. Add concessions (POP + SODA triggers combo discount)
        System.out.println("\n--- Adding Concessions ---");
        System.out.println("Add POP:  " + engine.addConcession(cart, "POP",  1));
        System.out.println("Add SODA: " + engine.addConcession(cart, "SODA", 1));
        System.out.println("Add invalid item: " + engine.addConcession(cart, "GHOST", 1));
        System.out.println("Add qty 0: " + engine.addConcession(cart, "POP", 0));
        
        // 8. Print receipt
        System.out.println("\n" + engine.getReceipt(cart));
    }
}