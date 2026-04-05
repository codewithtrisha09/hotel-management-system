package service;

import model.Room;
import model.Booking;
import model.Feedback;
import model.Bill;
import java.util.ArrayList;

public class HotelService {

    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static ArrayList<Feedback> feedbacks = new ArrayList<>();

    public static ArrayList<Room> getRooms() //default room setup
    {
        if (rooms.isEmpty()) //Lazy Initialization
        	{
            rooms.add(new Room(101, "Single", 1000));
            rooms.add(new Room(102, "Double", 2000));
            rooms.add(new Room(103, "Deluxe", 3000));
        }
        return rooms;
    }

    public static void addRoom(Room r) {
        rooms.add(r);
    }

    public static boolean bookRoom(String name, int roomNo, boolean wifi, int days,
                                   boolean member, int adults, int kids, boolean mattress) {

        for (Room r : rooms) {
            if (r.getRoomNo() == roomNo && r.isAvailable()) {

                double bill = r.getPrice() * days;

                if (wifi) bill += 500 * days;
                if (mattress) bill += 300 * days;
                if (member) bill *= 0.9;

                Booking b = new Booking(name, roomNo, wifi, days, member, bill, adults, kids, mattress);
                bookings.add(b);

                r.setAvailable(false);
                return true;
            }
        }
        return false;
    }

    public static Booking getBooking(int roomNo) //used for bill generation/checkout
    {
        for (Booking b : bookings) {
            if (b.getRoomNo() == roomNo) return b;
        }
        return null;
    }

    // ✅ BILL GENERATION
    public static Bill generateDetailedBill(Booking b) {

        double base = b.getBill();
        double gst = base * 0.10;
        double discount = b.isMember() ? base * 0.10 : 0;
        double total = base + gst - discount;

        return new Bill(
                b.getName(),
                b.getRoomNo(),
                b.getDays(),
                b.getAdults(),
                b.getKids(),
                b.isWifi(),
                b.isExtraMattress(),
                base,
                gst,
                discount,
                total
        );
    }

    // ✅ NEW: GET ALL BOOKINGS 
    public static ArrayList<Booking> getAllBookings() {
        return bookings;
    }

    // ✅ NEW: TOTAL REVENUE CALCULATION
    public static double getTotalRevenue() {
        double revenue = 0;

        for (Booking b : bookings) {
            double subtotal = b.getBill();
            double gst = subtotal * 0.10;
            double total = subtotal + gst;

            if (b.isMember()) total *= 0.9;

            revenue += total;
        }

        return revenue;
    }

    public static void checkout(int roomNo) {
        Room foundRoom = null;

        for (Room r : rooms) {
            if (r.getRoomNo() == roomNo) {
                foundRoom = r;
                break;
            }
        }

        if (foundRoom == null) return;

        foundRoom.setCleaning(true);
        foundRoom.setAvailable(false);

        final Room room = foundRoom;

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                room.setCleaning(false);
                room.setAvailable(true);

                javafx.application.Platform.runLater(() ->
                        ui.RoomView.refreshTable()
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void addFeedback(Feedback f) {
        feedbacks.add(f);
    }

    public static ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public static ArrayList<Booking> getRevenueBookings() {
        return new ArrayList<>(bookings);
    }
}